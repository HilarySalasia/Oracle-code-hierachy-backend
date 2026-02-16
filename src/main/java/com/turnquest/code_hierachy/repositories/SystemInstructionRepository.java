package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.models.SystemInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SystemInstructionRepository extends JpaRepository<SystemInstruction, Long> {
    Optional<SystemInstruction> findByInstructionName(String instructionName);
}