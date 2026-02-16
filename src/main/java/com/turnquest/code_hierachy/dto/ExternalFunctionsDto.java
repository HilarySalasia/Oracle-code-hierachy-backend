package com.turnquest.code_hierachy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ExternalFunctionsDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String calledPackageName;
    String calledFunctionName;
    Integer packagesProcedureId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    HierarchyPackagesDto externalHierarchy;
}
