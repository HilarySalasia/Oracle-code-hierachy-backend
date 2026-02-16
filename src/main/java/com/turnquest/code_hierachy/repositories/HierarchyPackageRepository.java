package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.models.HierarchyPackages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HierarchyPackageRepository extends JpaRepository<HierarchyPackages, Long> {

    @Query("SELECT h FROM HierarchyPackages h WHERE h.packageName = ?1 AND h.procedureFunctionName = ?2")
    List<HierarchyPackages> findByPackageNameAndProcedureFunctionName(String calledPackageName,
                                                                      String calledFunctionName);
}