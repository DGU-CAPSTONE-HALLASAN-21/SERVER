package org.dgu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OracleAiService {
    private final SignatureGenerator signatureGenerator;
    private final OracleProperties oracleProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateText(String prompt) throws Exception {
        String endpoint = String.format("https://inference.generativeai.%s.oci.oraclecloud.com/20231130/actions/generateText", oracleProperties.getRegion());

        Map<String, Object> body = Map.of(
                "servingMode", Map.of(
                        "modelId", oracleProperties.getModelId(),
                        "servingType", "ON_DEMAND"
                ),
                "compartmentId", oracleProperties.getCompartmentId(),
                "input", prompt
        );

        HttpHeaders headers = signatureGenerator.generateHeaders(endpoint, "POST", body);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }
}
