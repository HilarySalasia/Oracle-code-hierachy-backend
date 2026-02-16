package com.turnquest.code_hierachy.service;

import com.turnquest.code_hierachy.dto.HierarchyPackagesDto;
import com.turnquest.code_hierachy.models.HierarchyPackages;

import java.util.List;


public interface HierarchyPackageService {
    HierarchyPackages save(HierarchyPackages hierarchyPackages);
    HierarchyPackages findById(Long id);
    List<HierarchyPackages> findAll();

    HierarchyPackagesDto findHierarchyByCalledPackageNameAndCalledFunctionName(String calledPackageName,
                                                                               String calledFunctionName);

    HierarchyPackagesDto getHierarchyPackageDto(List<HierarchyPackages> hierarchyPackages);
    void deleteById(Long id);
}