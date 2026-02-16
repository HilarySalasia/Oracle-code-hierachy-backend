//package com.turnquest.code_hierachy.controller;
//
//import com.turnquest.code_hierachy.enums.PlsqlObjectType;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Slf4j
//public class ConversionController {
//
//    @Autowired
//    private ConversionService conversionService;
//
//    @PostMapping("/convert")
//    public ResponseEntity<String> convertPlsqlObject(
//            @RequestParam String objectName,
//            @RequestParam PlsqlObjectType objectType,
//            @RequestParam String instructionName) {
//
//        try {
//            conversionService.convertPlsqlObject(objectName, objectType, instructionName);
//            return ResponseEntity.ok("Conversion initiated successfully for " + objectName);
//        } catch (Exception e) {
//            log.error("An exception occurred", e);
//            return ResponseEntity.badRequest().body("Conversion failed: " + e.getMessage());
//        }
//    }
//}