package com.turnquest.code_hierachy.service;

import com.turnquest.code_hierachy.models.Packages;
import java.util.List;

public interface PackageService {
    Packages save(Packages pkg);
    Packages findById(Long id);
    List<Packages> findAll();
    void deleteById(Long id);
    List<String> findPackageNamesByOwner(String owner);
}