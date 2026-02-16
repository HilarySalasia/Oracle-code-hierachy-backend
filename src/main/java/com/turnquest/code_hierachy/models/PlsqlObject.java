package com.turnquest.code_hierachy.models;

import com.turnquest.code_hierachy.enums.PlsqlObjectType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "plsql_object")
@Getter
@Setter
public class PlsqlObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_name", unique = true, nullable = false)
    private String objectName;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type", nullable = false)
    private PlsqlObjectType objectType;

    @Column(name = "oracle_schema")
    private String oracleSchema;

    @Lob  // For large text
    @Column(name = "plsql_code", nullable = false)
    private String plsqlCode;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()")
    private OffsetDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}