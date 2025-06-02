package org.dgu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.asn1.ASN1Sequence;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
public class SignatureGenerator {

    private final OracleProperties oracleProperties;

    public SignatureGenerator(OracleProperties oracleProperties) {
        this.oracleProperties = oracleProperties;
    }

    public HttpHeaders generateHeaders(String url, String method, Map<String, Object> body) throws Exception {
        String region = oracleProperties.getRegion();
        String userOcid = oracleProperties.getUserOcid();
        String tenancyOcid = oracleProperties.getTenancyOcid();
        String keyFingerprint = oracleProperties.getKeyFingerprint();
        String privateKeyPath = oracleProperties.getPrivateKeyPath();

        // 날짜 생성 (RFC 1123 형식)
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));

        // 바디를 JSON으로 직렬화
        ObjectMapper mapper = new ObjectMapper();
        String bodyJson = mapper.writeValueAsString(body);

        // digest 해시 생성
        String digest = "SHA-256=" + Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-256").digest(bodyJson.getBytes(StandardCharsets.UTF_8))
        );

        // URL 분석
        URI uri = new URI(url);
        String path = uri.getRawPath(); // 예: "/20231130/actions/generateText"
        String hostValue = uri.getHost(); // 예: "inference.generativeai.us-chicago-1.oci.oraclecloud.com"

        // 서명 문자열 구성
        String requestTarget = "(request-target): " + method.toLowerCase() + " " + path;
        String hostHeader = "host: " + hostValue;
        String dateHeader = "date: " + date;
        String digestHeader = "digest: " + digest;

        String signingString = String.join("\n", requestTarget, hostHeader, dateHeader, digestHeader);
        String signature = signWithPrivateKey(signingString, privateKeyPath);

        // Authorization 헤더 구성
        String authHeader = String.format(
                "Signature version=\"1\",keyId=\"%s/%s/%s\",algorithm=\"rsa-sha256\",headers=\"(request-target) host date digest\",signature=\"%s\"",
                tenancyOcid, userOcid, keyFingerprint, signature
        );

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("host", hostValue);
        headers.set("date", date);
        headers.set("digest", digest);
        headers.set("Authorization", authHeader);

        return headers;
    }


    private String signWithPrivateKey(String data, String privateKeyPath) throws Exception {
        PrivateKey privateKey = loadPrivateKey(privateKeyPath);
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(sig.sign());
    }

    private PrivateKey loadPrivateKey(String privateKeyPath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(privateKeyPath));
        StringBuilder keyBuilder = new StringBuilder();
        for (String line : lines) {
            if (line.contains("PRIVATE KEY")) continue; // 헤더/푸터 제거
            if (line.contains("OCI_API_KEY")) continue; // OCI 주석 제거
            keyBuilder.append(line.trim());
        }

        byte[] keyBytes = Base64.getDecoder().decode(keyBuilder.toString());

        try {
            // Try PKCS#8 first
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            // Fallback to PKCS#1
            return loadPKCS1PrivateKey(keyBytes);
        }
    }


    private PrivateKey loadPKCS1PrivateKey(byte[] pkcs1Bytes) throws Exception {
        // Add PKCS#1 DER to PKCS#8 wrapper manually
        ASN1Sequence primitive = (ASN1Sequence) ASN1Sequence.fromByteArray(pkcs1Bytes);
        org.bouncycastle.asn1.pkcs.RSAPrivateKey rsa = org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(primitive);

        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(
                rsa.getModulus(),
                rsa.getPublicExponent(),
                rsa.getPrivateExponent(),
                rsa.getPrime1(),
                rsa.getPrime2(),
                rsa.getExponent1(),
                rsa.getExponent2(),
                rsa.getCoefficient()
        );

        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }


}
