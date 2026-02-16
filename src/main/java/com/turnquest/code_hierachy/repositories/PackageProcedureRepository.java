package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.models.PackageProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageProcedureRepository extends JpaRepository<PackageProcedure, Long> {
}