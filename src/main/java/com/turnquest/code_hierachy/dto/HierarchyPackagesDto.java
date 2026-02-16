package com.turnquest.code_hierachy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class HierarchyPackagesDto {
    String packageName;
    String procedureFunctionName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String owner;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<InternalFunctionsDto> internalFunctionsDtos;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<ExternalFunctionsDto> externalFunctionsDtos;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<ExternalFunctionsDto> externalUnknownFunctionsDtos;


}
