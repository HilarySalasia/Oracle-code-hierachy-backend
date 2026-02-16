package com.turnquest.code_hierachy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Entity
@Data
public class HierarchyPackages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String owner;
    private String packageName;
    private String procedureFunctionName;
    private String procedureFunctionType;
    private String callType;
    private String calledPackageName;
    private String calledFunctionName;
    private Integer packagesProcedureId;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private HierarchyPackages parent;
}