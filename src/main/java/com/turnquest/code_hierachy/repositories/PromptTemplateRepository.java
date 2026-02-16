package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.models.PromptTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptTemplateRepository extends JpaRepository<PromptTemplate, Long> {
}
