package org.dgu;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.generativeaiinference.GenerativeAiInferenceClient;
import com.oracle.bmc.generativeaiinference.model.*;
import com.oracle.bmc.generativeaiinference.requests.GenerateTextRequest;
import com.oracle.bmc.generativeaiinference.responses.GenerateTextResponse;
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
    private final AuthenticationDetailsProvider authProvider;
    private final OracleProperties oracleProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    public LlmInferenceResponse generateText(String prompt) throws Exception {
        String endpoint = String.format("https://inference.generativeai.%s.oci.oraclecloud.com/20231130/actions/generateText", oracleProperties.getRegion());
        GenerativeAiInferenceClient client = new GenerativeAiInferenceClient(authProvider);
        client.setRegion(Region.US_CHICAGO_1);

        CohereLlmInferenceRequest llmRequest = CohereLlmInferenceRequest.builder()
                .prompt(prompt)
                .build();

        GenerateTextRequest request = GenerateTextRequest.builder()
                .generateTextDetails(
                        GenerateTextDetails.builder()
                                .compartmentId(oracleProperties.getCompartmentId())
                                .servingMode(
                                        OnDemandServingMode.builder()
                                                .modelId(oracleProperties.getModelId())
                                                .build()
                                )
                                .inferenceRequest(llmRequest)
                                .build()
                )
                .build();

        GenerateTextResponse response = client.generateText(request);
        return response.getGenerateTextResult().getInferenceResponse();
    }
}