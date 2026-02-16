package com.turnquest.code_hierachy.controller;

import com.turnquest.code_hierachy.dto.HierarchyPackagesDto;
import com.turnquest.code_hierachy.models.HierarchyFunctions;
import com.turnquest.code_hierachy.service.HierarchyFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hierarchy-functions")
public class HierarchyFunctionController {

    private final HierarchyFunctionService hierarchyFunctionService;

    @Autowired
    public HierarchyFunctionController(HierarchyFunctionService hierarchyFunctionService) {
        this.hierarchyFunctionService = hierarchyFunctionService;
    }

    @PostMapping
    public ResponseEntity<HierarchyFunctions> createHierarchyFunction(@RequestBody HierarchyFunctions hierarchyFunctions) {
        HierarchyFunctions savedFunction = hierarchyFunctionService.save(hierarchyFunctions);
        return ResponseEntity.ok(savedFunction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HierarchyFunctions> getHierarchyFunctionById(@PathVariable Long id) {
        HierarchyFunctions hierarchyFunctions = hierarchyFunctionService.findById(id);
        if (hierarchyFunctions != null) {
            return ResponseEntity.ok(hierarchyFunctions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<HierarchyFunctions>> getAllHierarchyFunctions() {
        List<HierarchyFunctions> hierarchyFunctions = hierarchyFunctionService.findAll();
        return ResponseEntity.ok(hierarchyFunctions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHierarchyFunctionById(@PathVariable Long id) {
        hierarchyFunctionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<List<HierarchyPackagesDto>> findHierarchyByFunctionName(@RequestParam String calledFunctionName) {
        List<HierarchyPackagesDto> hierarchyPackagesDto = hierarchyFunctionService.findHierarchyByFunctionName(calledFunctionName);
        return ResponseEntity.ok(hierarchyPackagesDto);
    }
}