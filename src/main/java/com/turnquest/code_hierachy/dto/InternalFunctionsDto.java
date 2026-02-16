package com.turnquest.code_hierachy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class InternalFunctionsDto {
    String calledFunctionName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    HierarchyPackagesDto internalHierarchy;
}
