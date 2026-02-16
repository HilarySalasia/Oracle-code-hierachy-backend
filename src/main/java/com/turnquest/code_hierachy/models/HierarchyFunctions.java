package com.turnquest.code_hierachy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class HierarchyFunctions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String objectName;
    private String functionName;
    private String functionType;
    private String packageName;
    private String packageFunctionName;
    private Integer packageProcedureId;
}