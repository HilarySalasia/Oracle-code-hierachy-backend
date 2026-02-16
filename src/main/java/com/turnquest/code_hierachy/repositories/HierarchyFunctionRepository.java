package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.models.HierarchyFunctions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HierarchyFunctionRepository extends JpaRepository<HierarchyFunctions, Long> {

    @Query("SELECT h FROM HierarchyFunctions h WHERE h.objectName = ?1 ")
    List<HierarchyFunctions> findByFunctionName(String calledFunctionName);
}