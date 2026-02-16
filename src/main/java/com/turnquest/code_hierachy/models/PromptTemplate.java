package com.turnquest.code_hierachy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "prompt_template")
@Getter
@Setter
public class PromptTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_name", unique = true, nullable = false)
    private String templateName;

    @Lob // For large text
    @Column(name = "template_text", nullable = false)
    private String templateText;

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