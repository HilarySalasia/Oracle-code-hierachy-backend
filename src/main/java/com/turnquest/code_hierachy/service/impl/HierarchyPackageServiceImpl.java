package com.turnquest.code_hierachy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turnquest.code_hierachy.dto.ExternalFunctionsDto;
import com.turnquest.code_hierachy.dto.HierarchyPackagesDto;
import com.turnquest.code_hierachy.dto.InternalFunctionsDto;
import com.turnquest.code_hierachy.models.HierarchyPackages;
import com.turnquest.code_hierachy.repositories.HierarchyPackageRepository;
import com.turnquest.code_hierachy.service.HierarchyPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HierarchyPackageServiceImpl implements HierarchyPackageService {

    @Autowired
    private HierarchyPackageRepository hierarchyPackageRepository;

    public HierarchyPackages save(HierarchyPackages hierarchyPackages) {
        return hierarchyPackageRepository.save(hierarchyPackages);
    }

    @Override
    public HierarchyPackages findById(Long id) {
        return hierarchyPackageRepository.findById(id).orElse(null);
    }

    @Override
    public List<HierarchyPackages> findAll() {
        return hierarchyPackageRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        hierarchyPackageRepository.deleteById(id);
    }

    @Override
    public HierarchyPackagesDto findHierarchyByCalledPackageNameAndCalledFunctionName(String calledPackageName,
                                                                                         String calledFunctionName) {
       List<HierarchyPackages> hierarchyPackages = hierarchyPackageRepository
               .findByPackageNameAndProcedureFunctionName(calledPackageName, calledFunctionName);
       return getHierarchyPackageDto(hierarchyPackages);
    }

    @Override
    public HierarchyPackagesDto getHierarchyPackageDto(List<HierarchyPackages> hierarchyPackages) {
        HierarchyPackagesDto hierarchyPackagesDto = new HierarchyPackagesDto();
        hierarchyPackagesDto.setPackageName(hierarchyPackages.get(0).getPackageName());
        hierarchyPackagesDto.setProcedureFunctionName(hierarchyPackages.get(0).getProcedureFunctionName());
        hierarchyPackagesDto.setOwner(hierarchyPackages.get(0).getOwner());
        List<InternalFunctionsDto> internalFunctionsDtos = new ArrayList<>();
        List<ExternalFunctionsDto> externalFunctionsDtos = new ArrayList<>();
        for(HierarchyPackages hierarchyPackage : hierarchyPackages) {
            if (hierarchyPackage.getCallType().equals("INTERNAL") && hierarchyPackage.getCalledFunctionName() != null){
                InternalFunctionsDto internalFunctionsDto = new InternalFunctionsDto();
                internalFunctionsDto.setCalledFunctionName(hierarchyPackage.getCalledFunctionName());
                internalFunctionsDtos.add(internalFunctionsDto);
            } else if (hierarchyPackage.getCallType().equals("EXTERNAL") ) {
                ExternalFunctionsDto externalFunctionsDto = new ExternalFunctionsDto();
                externalFunctionsDto.setCalledPackageName(hierarchyPackage.getCalledPackageName());
                externalFunctionsDto.setCalledFunctionName(hierarchyPackage.getCalledFunctionName());
                externalFunctionsDto.setPackagesProcedureId(hierarchyPackage.getPackagesProcedureId());
                List<HierarchyPackages> hierarchyPackagesList = hierarchyPackageRepository.findByPackageNameAndProcedureFunctionName(
                        hierarchyPackage.getCalledPackageName(),
                        hierarchyPackage.getCalledFunctionName());
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(hierarchyPackagesList);
                    System.out.println(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!hierarchyPackagesList.isEmpty()) {
                    externalFunctionsDto.setExternalHierarchy(this.getHierarchyPackageDto(hierarchyPackagesList));
                }
                externalFunctionsDtos.add(externalFunctionsDto);
            }
        }
        hierarchyPackagesDto.setInternalFunctionsDtos(internalFunctionsDtos);
        hierarchyPackagesDto.setExternalFunctionsDtos(externalFunctionsDtos);

        return hierarchyPackagesDto;
    }

//    @Override
//    public HierarchyPackage getHierarchy(Long id) {
//        HierarchyPackage hierarchyPackage = hierarchyPackageRepository.findById(id).orElse(null);
//        if (hierarchyPackage != null) {
//            // Fetch internal and external procedures
//
//        }
//        return hierarchyPackage;
//    }
}