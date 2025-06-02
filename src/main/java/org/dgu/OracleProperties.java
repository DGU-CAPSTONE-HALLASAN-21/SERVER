package org.dgu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "oracle")
public class OracleProperties {
    private String userOcid;
    private String tenancyOcid;
    private String keyFingerprint;
    private String privateKeyPath;
    private String region;
    private String compartmentId;
    private String modelId;
    private String passphrase;
    private String identityDomainId;
    private String vcnOcid;
    private String subnetOcid;
    private String clusterOcid;
}
