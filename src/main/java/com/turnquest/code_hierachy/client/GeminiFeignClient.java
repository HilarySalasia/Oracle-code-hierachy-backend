package com.turnquest.code_hierachy.client;

import com.turnquest.code_hierachy.models.GenerateContentRequest;
import com.turnquest.code_hierachy.models.GenerateContentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geminiClient", url = "https://generativelanguage.googleapis.com")
public interface GeminiFeignClient {

    @PostMapping("/v1beta/models/gemini-2.0-flash:generateContent")
    GenerateContentResponse generateContent(
            @RequestParam("key") String apiKey,
            @RequestBody GenerateContentRequest request);
}