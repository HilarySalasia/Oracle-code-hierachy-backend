package com.turnquest.code_hierachy.service;

import com.turnquest.code_hierachy.models.PackageDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PackageDescriptionService {
    void generateAndSavePackageDescriptions(String owner);
    void processAndSavePackageDescription(String owner, String packageName);
    List<PackageDescription> getPackageDescriptions(String owner);
    
    // New CRUD methods
    PackageDescription getPackageDescriptionById(Long id);
    PackageDescription getPackageDescriptionByOwnerAndName(String owner, String packageName);
    PackageDescription updatePackageDescription(Long id, PackageDescription packageDescription);
    void deletePackageDescription(Long id);
    void deletePackageDescriptionsByOwner(String owner);

    // Additional fetch methods
    Page<PackageDescription> getAllPackageDescriptions(Pageable pageable);
    List<PackageDescription> getPackageDescriptionsByPackageName(String packageName);
}