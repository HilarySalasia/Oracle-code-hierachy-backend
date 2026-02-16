package com.turnquest.code_hierachy.controller;

import com.turnquest.code_hierachy.dto.HierarchyPackagesDto;
import com.turnquest.code_hierachy.models.HierarchyPackages;
import com.turnquest.code_hierachy.service.HierarchyPackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hierachypackages")
public class HierarchyPackageController {

    private final HierarchyPackageService hierarchyPackageService;

    public HierarchyPackageController(HierarchyPackageService hierarchyPackageService) {
        this.hierarchyPackageService = hierarchyPackageService;
    }


    @GetMapping("/{packageName}/{functionName}")
    public ResponseEntity<HierarchyPackagesDto> getHierarchyPackage(@PathVariable String packageName, @PathVariable String functionName) {
        HierarchyPackagesDto hierarchyPackages = hierarchyPackageService.findHierarchyByCalledPackageNameAndCalledFunctionName(packageName, functionName);
        return ResponseEntity.ok().body(hierarchyPackages);
    }


    @GetMapping
    public ResponseEntity<List<HierarchyPackages>> getAllHierarchyPackages() {
        List<HierarchyPackages> hierarchyPackages = hierarchyPackageService.findAll();
        return ResponseEntity.ok().body(hierarchyPackages);
    }

}