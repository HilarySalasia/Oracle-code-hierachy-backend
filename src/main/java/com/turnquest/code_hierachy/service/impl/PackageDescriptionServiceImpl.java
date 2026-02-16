package com.turnquest.code_hierachy.service.impl;

import com.turnquest.code_hierachy.client.GeminiFeignClient;
import com.turnquest.code_hierachy.models.GenerateContentRequest;
import com.turnquest.code_hierachy.models.GenerateContentResponse;
import com.turnquest.code_hierachy.models.PackageDescription;
import com.turnquest.code_hierachy.repositories.PackageDescriptionRepository;
import com.turnquest.code_hierachy.service.PackageDescriptionService;
import com.turnquest.code_hierachy.service.PackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageDescriptionServiceImpl implements PackageDescriptionService {

    private final GeminiFeignClient geminiClient;
    private final PackageDescriptionRepository packageDescriptionRepository;
    private final PackageService packageService;

    @Qualifier("oracleJdbcTemplate")
    private final JdbcTemplate oracleJdbcTemplate;

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Override
    @Transactional
    public void generateAndSavePackageDescriptions(String owner) {
        try {
            // Get all packages for the owner
            List<String> packageNames = packageService.findPackageNamesByOwner(owner);
            log.info("Found {} packages for owner: {}", packageNames.size(), owner);

            for (String packageName : packageNames) {
                processAndSavePackageDescription(owner, packageName);
            }
        } catch (Exception e) {
            log.error("Error generating package descriptions for owner: {}", owner, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void processAndSavePackageDescription(String owner, String packageName) {
        try {
            log.info("Processing package: {} for owner: {}", packageName, owner);
            
            // Get package body
            String packageBody = getPackageBody(owner, packageName);
            
            // Generate description using Gemini
            String description = generateDescription(packageBody);
            
            // Save to database
            savePackageDescription(owner, packageName, description);
            
            log.info("Successfully processed package: {} for owner: {}", packageName, owner);
        } catch (Exception e) {
            log.error("Error processing package: {} for owner: {}", packageName, owner, e);
            throw e;
        }
    }

    private String getPackageBody(String owner, String packageName) {
        String sql = "SELECT text FROM all_source WHERE owner = ? AND name = ? AND type = 'PACKAGE BODY' ORDER BY line";
        List<String> lines = oracleJdbcTemplate.queryForList(sql, String.class, owner, packageName);
        return String.join("\n", lines);
    }

    private String generateDescription(String packageBody) {
        String prompt = """
            Translate the following Oracle PL/SQL package or block into natural language, explaining every statement in detail while maintaining execution flow. The explanation should cover:

            Package Structure: Describe whether the code defines a package specification, body, or standalone block.

            Function and Procedure Details: Include function or procedure names, input parameters, output parameters, and return values, if applicable.

            Variable Declarations: Explain all variables and constants, their data types, and their intended use.

            Control Structures: Describe the purpose of loops (FOR, WHILE), conditional statements (IF-ELSE), and exception handling mechanisms.

            SQL Operations: Explain how SELECT, INSERT, UPDATE, DELETE, and cursor operations retrieve or manipulate data.

            Error Handling: Identify exception handling strategies, including predefined and user-defined exceptions.

            Execution Flow: Maintain a sequential explanation without quoting the code directly, ensuring clarity for non-developers while preserving the logical structure.

            Here's the package body to analyze:

            %s
            """.formatted(packageBody);

        GenerateContentRequest request = new GenerateContentRequest();
        GenerateContentRequest.Content content = new GenerateContentRequest.Content();
        content.setRole("user");
        GenerateContentRequest.Part part = new GenerateContentRequest.Part();
        part.setText(prompt);
        content.setParts(List.of(part));
        request.setContents(List.of(content));

        GenerateContentResponse response = geminiClient.generateContent(
            geminiApiKey,
            request
        );

        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    private void savePackageDescription(String owner, String packageName, String description) {
        PackageDescription packageDescription = new PackageDescription();
        packageDescription.setOwner(owner);
        packageDescription.setPackageName(packageName);
        packageDescription.setDescription(description);
        
        packageDescriptionRepository.save(packageDescription);
    }

    @Override
    public List<PackageDescription> getPackageDescriptions(String owner) {
        return packageDescriptionRepository.findByOwner(owner);
    }

    @Override
    public PackageDescription getPackageDescriptionById(Long id) {
        return packageDescriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Package description not found with id: " + id));
    }

    @Override
    public PackageDescription getPackageDescriptionByOwnerAndName(String owner, String packageName) {
        return packageDescriptionRepository.findByOwnerAndPackageName(owner, packageName)
            .orElseThrow(() -> new RuntimeException("Package description not found for owner: " + owner + " and package: " + packageName));
    }

    @Override
    @Transactional
    public PackageDescription updatePackageDescription(Long id, PackageDescription packageDescription) {
        PackageDescription existingDescription = getPackageDescriptionById(id);
        existingDescription.setDescription(packageDescription.getDescription());
        return packageDescriptionRepository.save(existingDescription);
    }

    @Override
    @Transactional
    public void deletePackageDescription(Long id) {
        packageDescriptionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deletePackageDescriptionsByOwner(String owner) {
        packageDescriptionRepository.deleteByOwner(owner);
    }

    @Override
    public Page<PackageDescription> getAllPackageDescriptions(Pageable pageable) {
        return packageDescriptionRepository.findAll(pageable);
    }

    @Override
    public List<PackageDescription> getPackageDescriptionsByPackageName(String packageName) {
        return packageDescriptionRepository.findByPackageName(packageName);
    }
} 