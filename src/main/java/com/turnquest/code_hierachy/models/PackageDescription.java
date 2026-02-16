package com.turnquest.code_hierachy.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "package_descriptions")
@Data
public class PackageDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_name", nullable = false)
    private String packageName;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
} 