package com.turnquest.code_hierachy.service;

import com.turnquest.code_hierachy.dto.HierarchyPackagesDto;
import com.turnquest.code_hierachy.models.HierarchyFunctions;
import java.util.List;

public interface HierarchyFunctionService {
    HierarchyFunctions save(HierarchyFunctions hierarchyFunctions);
    HierarchyFunctions findById(Long id);
    List<HierarchyFunctions> findAll();
    void deleteById(Long id);

    List<HierarchyPackagesDto> findHierarchyByFunctionName(String calledFunctionName);
}