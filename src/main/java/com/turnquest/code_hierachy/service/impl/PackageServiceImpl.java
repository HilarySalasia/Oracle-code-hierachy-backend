package com.turnquest.code_hierachy.service.impl;

import com.turnquest.code_hierachy.models.Packages;
import com.turnquest.code_hierachy.repositories.PackagesRepository;
import com.turnquest.code_hierachy.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackagesRepository packageRepository;

    @Override
    public Packages save(Packages pkg) {
        return packageRepository.save(pkg);
    }

    @Override
    public Packages findById(Long id) {
        return packageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Packages> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        packageRepository.deleteById(id);
    }

    @Override
    public List<String> findPackageNamesByOwner(String owner) {
        return packageRepository.findPackageNamesByOwner(owner);
    }


}