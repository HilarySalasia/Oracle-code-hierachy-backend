package com.turnquest.code_hierachy.repositories;

import com.turnquest.code_hierachy.enums.PlsqlObjectType;
import com.turnquest.code_hierachy.models.PlsqlObject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlsqlObjectRepository extends JpaRepository<PlsqlObject, Long> {
    Optional<PlsqlObject> findByObjectNameAndObjectType(String objectName, PlsqlObjectType objectType);
}
