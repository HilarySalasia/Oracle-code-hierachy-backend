package com.turnquest.code_hierachy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turnquest.code_hierachy.dto.ExternalFunctionsDto;
import com.turnquest.code_hierachy.dto.HierarchyPackagesDto;
import com.turnquest.code_hierachy.dto.InternalFunctionsDto;
import com.turnquest.code_hierachy.models.HierarchyFunctions;
import com.turnquest.code_hierachy.models.HierarchyPackages;
import com.turnquest.code_hierachy.repositories.HierarchyFunctionRepository;
import com.turnquest.code_hierachy.repositories.HierarchyPackageRepository;
import com.turnquest.code_hierachy.service.HierarchyFunctionService;
import com.turnquest.code_hierachy.service.HierarchyPackageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HierarchyFunctionServiceImpl implements HierarchyFunctionService {


    private final HierarchyFunctionRepository hierarchyFunctionRepository;
    private final HierarchyPackageService hierarchyPackageService;

    private final HierarchyPackageRepository hierarchyPackageRepository;

    public HierarchyFunctionServiceImpl(HierarchyFunctionRepository hierarchyFunctionRepository,
                                        HierarchyPackageService hierarchyPackageService,
                                        HierarchyPackageRepository hierarchyPackageRepository) {
        this.hierarchyFunctionRepository = hierarchyFunctionRepository;
        this.hierarchyPackageService = hierarchyPackageService;
        this.hierarchyPackageRepository = hierarchyPackageRepository;
    }

    @Override
    public HierarchyFunctions save(HierarchyFunctions hierarchyFunctions) {
        return hierarchyFunctionRepository.save(hierarchyFunctions);
    }

    @Override
    public HierarchyFunctions findById(Long id) {
        return hierarchyFunctionRepository.findById(id).orElse(null);
    }

    @Override
    public List<HierarchyFunctions> findAll() {
        return hierarchyFunctionRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        hierarchyFunctionRepository.deleteById(id);
    }

    @Override
    public List<HierarchyPackagesDto> findHierarchyByFunctionName(String calledFunctionName) {

        List<HierarchyFunctions> hierarchyPackages = hierarchyFunctionRepository.findByFunctionName(calledFunctionName);
       return getHierarchyFunctions(hierarchyPackages);
    }


    private List<HierarchyPackagesDto> getHierarchyFunctions(List<HierarchyFunctions> hierarchyFunctions) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(hierarchyFunctions);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<HierarchyPackagesDto> hierarchyPackagesDtoList = new ArrayList<>();
        for(HierarchyFunctions hierarchyFunction : hierarchyFunctions) {
            HierarchyPackagesDto hierarchyPackagesDto = new HierarchyPackagesDto();
            hierarchyPackagesDto.setPackageName(hierarchyFunctions.get(0).getObjectName());
            hierarchyPackagesDto.setProcedureFunctionName(hierarchyFunction.getFunctionName());
    //        hierarchyPackagesDto.setOwner(hierarchyFunctions.get(0).get());
            List<InternalFunctionsDto> internalFunctionsDtos = new ArrayList<>();
            List<ExternalFunctionsDto> externalFunctionsDtos = new ArrayList<>();
            List<ExternalFunctionsDto> externalUnknownFunctionsDtos = new ArrayList<>();

            if (hierarchyFunction.getFunctionType().equals("INTERNAL") && hierarchyFunction.getFunctionName() != null){
                InternalFunctionsDto internalFunctionsDto = new InternalFunctionsDto();
                internalFunctionsDto.setCalledFunctionName(hierarchyFunction.getPackageFunctionName());
                internalFunctionsDtos.add(internalFunctionsDto);
            } else if (hierarchyFunction.getFunctionType().equals("EXTERNAL") ) {
                ExternalFunctionsDto externalFunctionsDto = new ExternalFunctionsDto();
                externalFunctionsDto.setCalledPackageName(hierarchyFunction.getPackageName());
                externalFunctionsDto.setCalledFunctionName(hierarchyFunction.getPackageFunctionName());
                externalFunctionsDto.setPackagesProcedureId(hierarchyFunction.getPackageProcedureId());
                List<HierarchyPackages> hierarchyPackagesList = hierarchyPackageRepository.findByPackageNameAndProcedureFunctionName(
                        hierarchyFunction.getPackageName(),
                        hierarchyFunction.getPackageFunctionName());
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(hierarchyPackagesList);
                    System.out.println(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!hierarchyPackagesList.isEmpty()) {
                    externalFunctionsDto.setExternalHierarchy(hierarchyPackageService.getHierarchyPackageDto(hierarchyPackagesList));
                }
                externalFunctionsDtos.add(externalFunctionsDto);
            } else {
                ExternalFunctionsDto externalFunctionsDto = new ExternalFunctionsDto();
                externalFunctionsDto.setCalledPackageName(hierarchyFunction.getPackageName());
                externalFunctionsDto.setCalledFunctionName(hierarchyFunction.getPackageFunctionName());
                externalFunctionsDto.setPackagesProcedureId(hierarchyFunction.getPackageProcedureId());
                externalUnknownFunctionsDtos.add(externalFunctionsDto);
            }
            hierarchyPackagesDto.setInternalFunctionsDtos(internalFunctionsDtos);
            hierarchyPackagesDto.setExternalFunctionsDtos(externalFunctionsDtos);
            hierarchyPackagesDto.setExternalUnknownFunctionsDtos(externalUnknownFunctionsDtos);
            hierarchyPackagesDtoList.add(hierarchyPackagesDto);
        }


        return hierarchyPackagesDtoList;
    }
}