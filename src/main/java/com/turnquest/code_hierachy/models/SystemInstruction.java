package com.turnquest.code_hierachy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "system_instruction")
@Getter
@Setter
public class SystemInstruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "instruction_name", unique = true, nullable = false)
    private String instructionName;

    @ManyToOne
    @JoinColumn(name = "prompt_template_id", nullable = false)
    private PromptTemplate promptTemplate;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()")
    private OffsetDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}