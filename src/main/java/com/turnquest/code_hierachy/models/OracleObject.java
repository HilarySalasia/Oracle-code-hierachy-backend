package com.turnquest.code_hierachy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OracleObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String objectName;
    private String objectType;
    private Boolean isPackaged;
    private Integer lineCount;
    private String owner;
}