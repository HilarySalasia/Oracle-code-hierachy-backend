package com.turnquest.code_hierachy.controller;

import com.turnquest.code_hierachy.dto.PackageDescriptionRequest;
import com.turnquest.code_hierachy.models.PackageDescription;
import com.turnquest.code_hierachy.service.PackageDescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/package-descriptions")
@RequiredArgsConstructor
public class PackageDescriptionController {

    private final PackageDescriptionService packageDescriptionService;

    // Create - Generate new package descriptions
    @PostMapping("/generate")
    public ResponseEntity<String> generatePackageDescriptions(@RequestBody PackageDescriptionRequest request) {
        packageDescriptionService.generateAndSavePackageDescriptions(request.getOwner());
        return ResponseEntity.ok("Package descriptions generation started for owner: " + request.getOwner());
    }

    // Read - Get all package descriptions with pagination
    @GetMapping("/all")
    public ResponseEntity<Page<PackageDescription>> getAllPackageDescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        return ResponseEntity.ok(packageDescriptionService.getAllPackageDescriptions(pageRequest));
    }

    // Read - Get all package descriptions for an owner
    @GetMapping("/{owner}")
    public ResponseEntity<List<PackageDescription>> getPackageDescriptions(@PathVariable String owner) {
        return ResponseEntity.ok(packageDescriptionService.getPackageDescriptions(owner));
    }

    // Read - Get all package descriptions by package name
    @GetMapping("/package/{packageName}")
    public ResponseEntity<List<PackageDescription>> getPackageDescriptionsByPackageName(@PathVariable String packageName) {
        return ResponseEntity.ok(packageDescriptionService.getPackageDescriptionsByPackageName(packageName));
    }

    // Read - Get a specific package description by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<PackageDescription> getPackageDescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(packageDescriptionService.getPackageDescriptionById(id));
    }

    // Read - Get a specific package description by owner and package name
    @GetMapping("/{owner}/{packageName}")
    public ResponseEntity<PackageDescription> getPackageDescriptionByOwnerAndName(
            @PathVariable String owner,
            @PathVariable String packageName) {
        return ResponseEntity.ok(packageDescriptionService.getPackageDescriptionByOwnerAndName(owner, packageName));
    }

    // Update - Update a package description
    @PutMapping("/{id}")
    public ResponseEntity<PackageDescription> updatePackageDescription(
            @PathVariable Long id,
            @RequestBody PackageDescription packageDescription) {
        return ResponseEntity.ok(packageDescriptionService.updatePackageDescription(id, packageDescription));
    }

    // Delete - Delete a package description by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackageDescription(@PathVariable Long id) {
        packageDescriptionService.deletePackageDescription(id);
        return ResponseEntity.ok().build();
    }

    // Delete - Delete all package descriptions for an owner
    @DeleteMapping("/owner/{owner}")
    public ResponseEntity<Void> deletePackageDescriptionsByOwner(@PathVariable String owner) {
        packageDescriptionService.deletePackageDescriptionsByOwner(owner);
        return ResponseEntity.ok().build();
    }
} 