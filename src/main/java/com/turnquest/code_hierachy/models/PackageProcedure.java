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
public class PackageProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String packageName;
    private String procedureOwner;
    private String procedureName;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Packages parentPackages;
}