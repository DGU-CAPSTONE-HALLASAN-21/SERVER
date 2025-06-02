package org.dgu;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class OciConfig {

    @Bean
    public AuthenticationDetailsProvider authenticationDetailsProvider() throws IOException {
        ConfigFileReader.ConfigFile config = ConfigFileReader.parse("C:/Users/jieun/.oci/config", "DEFAULT");

        return SimpleAuthenticationDetailsProvider.builder()
                .tenantId(config.get("tenancy"))
                .userId(config.get("user"))
                .fingerprint(config.get("fingerprint"))
                .privateKeySupplier(new SimplePrivateKeySupplier(config.get("key_file")))
                .region(Region.fromRegionId(config.get("region")))
                .build();
    }
}
