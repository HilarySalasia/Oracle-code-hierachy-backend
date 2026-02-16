package com.turnquest.code_hierachy.controller;

import com.turnquest.code_hierachy.service.extlib.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @Autowired
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/generate")
    public String generateContent(@RequestBody String prompt) {
        return geminiService.getResponseFromPrompt(prompt);
    }
}