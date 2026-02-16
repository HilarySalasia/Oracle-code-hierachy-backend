package com.turnquest.code_hierachy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "packages")
@Data
public class Packages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "package_owner")
    private String packageOwner;
}