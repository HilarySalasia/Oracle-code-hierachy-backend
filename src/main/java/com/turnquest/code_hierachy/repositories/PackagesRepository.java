package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.models.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackagesRepository extends JpaRepository<Packages, Long> {

    @Query("SELECT p.packageName FROM Packages p WHERE p.packageOwner = ?1")
    List<String> findPackageNamesByOwner(String owner);
}