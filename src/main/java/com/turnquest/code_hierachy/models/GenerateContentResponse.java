package com.turnquest.code_hierachy.models;

import lombok.Data;
import java.util.List;

@Data
public class GenerateContentResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;
    private String generatedText;

    @Data
    public static class Candidate {
        private Content content;
        private String finishReason;
        private double avgLogprobs;
    }

    @Data
    public static class Content {
        private List<Part> parts;
        private String role;
    }

    @Data
    public static class Part {
        private String text;
    }

    @Data
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;
        private List<PromptTokensDetail> promptTokensDetails;
        private List<CandidatesTokensDetail> candidatesTokensDetails;
    }

    @Data
    public static class PromptTokensDetail {
        private String modality;
        private int tokenCount;
    }

    @Data
    public static class CandidatesTokensDetail {
        private String modality;
        private int tokenCount;
    }
}