package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.models.OracleObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OracleObjectRepository extends JpaRepository<OracleObject, Long> {
}