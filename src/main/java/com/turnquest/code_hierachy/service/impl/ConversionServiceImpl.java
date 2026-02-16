//package com.turnquest.code_hierachy.service.impl;
//
//import com.google.api.client.json.JsonObjectParser;
//import com.turnquest.code_hierachy.enums.ConversionType;
//import com.turnquest.code_hierachy.enums.PlsqlObjectType;
//import com.turnquest.code_hierachy.models.Conversion;
//import com.turnquest.code_hierachy.models.PlsqlObject;
//import com.turnquest.code_hierachy.models.PromptTemplate;
//import com.turnquest.code_hierachy.models.SystemInstruction;
//import com.turnquest.code_hierachy.repositories.ConversionRepository;
//import com.turnquest.code_hierachy.repositories.PlsqlObjectRepository;
//import com.turnquest.code_hierachy.repositories.PromptTemplateRepository;
//import com.turnquest.code_hierachy.repositories.SystemInstructionRepository;
//import com.turnquest.code_hierachy.service.extlib.GeminiService;
//import com.turnquest.code_hierachy.service.extlib.OracleService;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.OffsetDateTime;
//
//@Service
//@Slf4j
//public class ConversionServiceImpl {
//
//    @Autowired
//    private PlsqlObjectRepository plsqlObjectRepository;
//    @Autowired
//    private SystemInstructionRepository systemInstructionRepository;
//    @Autowired
//    private PromptTemplateRepository promptTemplateRepository;
//    @Autowired
//    private ConversionRepository conversionRepository;
//    @Autowired
//    private GeminiService geminiService;
//    @Autowired
//    private OracleService oracleService;
//
//    @Transactional // Ensure atomicity
//    public void convertPlsqlObject(String objectName, PlsqlObjectType objectType, String instructionName) {
//
//        PlsqlObject plsqlObject = plsqlObjectRepository.findByObjectNameAndObjectType(objectName, objectType)
//                .orElseGet(() -> {
//                    // Fetch from Oracle if not found in Postgres
//                    String plsqlCode = oracleService.getPlsqlCode(objectName, objectType.toString());
//                    if (plsqlCode == null) {
//                        throw new EntityNotFoundException("PL/SQL Object not found in Oracle: " + objectName);
//                    }
//                    PlsqlObject newPlsqlObject = new PlsqlObject();
//                    newPlsqlObject.setObjectName(objectName);
//                    newPlsqlObject.setObjectType(objectType);
//                    newPlsqlObject.setPlsqlCode(plsqlCode);
//                    newPlsqlObject.setCreatedAt(OffsetDateTime.now());
//                    newPlsqlObject.setUpdatedAt(OffsetDateTime.now());
//                    return plsqlObjectRepository.save(newPlsqlObject);
//                });
//
//        SystemInstruction systemInstruction = systemInstructionRepository.findByInstructionName(instructionName)
//                .orElseThrow(() -> new EntityNotFoundException("SystemInstruction not found: " + instructionName));
//
//        PromptTemplate promptTemplate = promptTemplateRepository.findById(systemInstruction.getPromptTemplate().getId())
//                .orElseThrow(() -> new EntityNotFoundException("PromptTemplate not found for instruction: " + instructionName));
//
//        String templateText = promptTemplate.getTemplateText();
//        String plsqlCode = plsqlObject.getPlsqlCode();
//
//        // Assemble the final prompt (using a simple replace)
//        String finalPrompt = templateText.replace("{plsql_code}", plsqlCode);
//
//        // Send to Gemini
//        String geminiResponse = geminiService.generateText(finalPrompt);  // Assuming your GeminiService does this.
//        saveConversionResults(plsqlObject, systemInstruction, geminiResponse);
//        // ... (Parse Gemini response and store results in the conversion table) ...
//    }
//
//    private void saveConversionResults(PlsqlObject plsqlObject, SystemInstruction systemInstruction, String geminiResponse) {
//        try{
//            JSONObject jsonResponse = new JSONObject(geminiResponse);
//
//            // Extract different parts of the conversion (Service, Repository, Entity, Summary)
//            // **This parsing logic depends on how Gemini formats its response!**
//            String serviceCode = jsonResponse.optString("serviceCode", null); // Example key: "serviceCode"
//            String repositoryCode = jsonResponse.optString("repositoryCode", null);
//            String entityCode = jsonResponse.optString("entityCode", null);
//            String summaryText = jsonResponse.optString("summary", null);
//
//
//            // Save each part as a separate conversion record if it exists
//            if (serviceCode != null && !serviceCode.isEmpty()) {
//                Conversion serviceConversion = createConversion(plsqlObject, systemInstruction, ConversionType.SERVICE, serviceCode, geminiResponse);
//                conversionRepository.save(serviceConversion);
//            }
//
//            if (repositoryCode != null && !repositoryCode.isEmpty()) {
//                Conversion repositoryConversion = createConversion(plsqlObject, systemInstruction, ConversionType.REPOSITORY, repositoryCode, geminiResponse);
//                conversionRepository.save(repositoryConversion);
//            }
//
//            if (entityCode != null && !entityCode.isEmpty()) {
//                Conversion entityConversion =  createConversion(plsqlObject, systemInstruction, ConversionType.ENTITY, entityCode, geminiResponse);
//                conversionRepository.save(entityConversion);
//            }
//
//            if (summaryText != null && !summaryText.isEmpty()) {
//                Conversion summaryConversion =   createConversion(plsqlObject, systemInstruction, ConversionType.SUMMARY, summaryText, geminiResponse);
//                conversionRepository.save(summaryConversion);
//            }
//        }catch (Exception ex){
//            log.error("An exception occurred", ex);
//        }
//
//    }
//
//    private Conversion createConversion(PlsqlObject plsqlObject, SystemInstruction systemInstruction, ConversionType conversionType, String generatedCode, String geminiResponse) {
//        Conversion conversion = new Conversion();
//        conversion.setPlsqlObject(plsqlObject);
//        conversion.setSystemInstruction(systemInstruction);
//        conversion.setConversionType(conversionType);
//        conversion.setGeneratedCode(generatedCode);
//        conversion.setGeminiResponse(geminiResponse);
//        return conversion;
//    }
//}