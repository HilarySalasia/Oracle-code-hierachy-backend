package com.turnquest.code_hierachy.service.extlib;

        import com.turnquest.code_hierachy.client.GeminiFeignClient;
        import com.turnquest.code_hierachy.models.GenerateContentRequest;
        import com.turnquest.code_hierachy.models.GenerateContentResponse;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Service;

        import java.util.List;

        @Service
        public class GeminiService {

            private final GeminiFeignClient geminiFeignClient;

            @Value("${gemini.api-key}")
            private String apiKey;

            public GeminiService(GeminiFeignClient geminiFeignClient) {
                this.geminiFeignClient = geminiFeignClient;
            }

            public String getResponseFromPrompt(String prompt) {
                System.out.println("Api Key: " + apiKey);
                GenerateContentRequest request = new GenerateContentRequest();
                GenerateContentRequest.Content content = new GenerateContentRequest.Content();
                GenerateContentRequest.Part part = new GenerateContentRequest.Part();
                part.setText(prompt);
                content.setParts(List.of(part));
                request.setContents(List.of(content));

                GenerateContentResponse response = geminiFeignClient.generateContent(apiKey, request);
                return response.getCandidates().getFirst().getContent().getParts().getLast().getText();
            }
        }