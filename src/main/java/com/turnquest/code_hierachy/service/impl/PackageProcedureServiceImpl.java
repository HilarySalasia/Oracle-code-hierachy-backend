package com.turnquest.code_hierachy.service.impl;

import com.turnquest.code_hierachy.models.PackageProcedure;
import com.turnquest.code_hierachy.repositories.PackageProcedureRepository;
import com.turnquest.code_hierachy.service.PackageProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PackageProcedureServiceImpl implements PackageProcedureService {

    @Autowired
    private PackageProcedureRepository packageProcedureRepository;

    @Override
    public PackageProcedure save(PackageProcedure packageProcedure) {
        return packageProcedureRepository.save(packageProcedure);
    }

    @Override
    public PackageProcedure findById(Long id) {
        return packageProcedureRepository.findById(id).orElse(null);
    }

    @Override
    public List<PackageProcedure> findAll() {
        return packageProcedureRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        packageProcedureRepository.deleteById(id);
    }
}