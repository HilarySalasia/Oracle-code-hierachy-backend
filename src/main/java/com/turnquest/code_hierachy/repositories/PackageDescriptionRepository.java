package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.models.PackageDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageDescriptionRepository extends JpaRepository<PackageDescription, Long> {
    List<PackageDescription> findByOwner(String owner);
    List<PackageDescription> findByPackageName(String packageName);
    Optional<PackageDescription> findByOwnerAndPackageName(String owner, String packageName);
    void deleteByOwner(String owner);
} 