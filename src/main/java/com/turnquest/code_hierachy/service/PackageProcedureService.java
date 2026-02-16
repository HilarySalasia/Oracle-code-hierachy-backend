package com.turnquest.code_hierachy.service;

import com.turnquest.code_hierachy.models.PackageProcedure;
import java.util.List;

public interface PackageProcedureService {
    PackageProcedure save(PackageProcedure packageProcedure);
    PackageProcedure findById(Long id);
    List<PackageProcedure> findAll();
    void deleteById(Long id);
}