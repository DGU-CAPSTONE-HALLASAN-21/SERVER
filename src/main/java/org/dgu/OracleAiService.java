package org.dgu;

import com.oracle.bmc.ClientConfiguration;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.generativeaiinference.GenerativeAiInferenceClient;
import com.oracle.bmc.generativeaiinference.model.CohereLlmInferenceRequest;
import com.oracle.bmc.generativeaiinference.model.GenerateTextDetails;
import com.oracle.bmc.generativeaiinference.model.LlmInferenceResponse;
import com.oracle.bmc.generativeaiinference.model.OnDemandServingMode;
import com.oracle.bmc.generativeaiinference.requests.GenerateTextRequest;
import com.oracle.bmc.generativeaiinference.responses.GenerateTextResponse;
import com.oracle.bmc.http.client.jersey3.Jersey3HttpProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OracleAiService {
    private final AuthenticationDetailsProvider authProvider;
    private final OracleProperties oracleProperties;

    public LlmInferenceResponse generateText(String prompt) throws Exception {
        String endpoint = String.format("https://inference.generativeai.%s.oci.oraclecloud.com/20231130/actions/generateText", oracleProperties.getRegion());
        ClientConfiguration clientConfiguration = ClientConfiguration.builder().build();

        GenerativeAiInferenceClient client = GenerativeAiInferenceClient.builder()
                .region(Region.US_CHICAGO_1)
                .httpProvider(new Jersey3HttpProvider())
                .build(authProvider);

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