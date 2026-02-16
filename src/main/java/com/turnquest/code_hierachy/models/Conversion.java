//package com.turnquest.code_hierachy.models;
//
//    import com.turnquest.code_hierachy.enums.ConversionType;
//    import jakarta.persistence.*;
//    import lombok.Data;
//    import org.hibernate.annotations.Type;
//    import org.hibernate.annotations.TypeDef;
//    import java.time.OffsetDateTime;
//    import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
//
//    @Entity
//    @Table(name = "conversion")
//    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
//    @Data
//    public class Conversion {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Long id;
//
//        @ManyToOne
//        @JoinColumn(name = "plsql_object_id", nullable = false)
//        private PlsqlObject plsqlObject;
//
//        @ManyToOne
//        @JoinColumn(name = "system_instruction_id", nullable = false)
//        private SystemInstruction systemInstruction;
//
//        @Enumerated(EnumType.STRING)
//        @Column(name = "conversion_type", nullable = false)
//        private ConversionType conversionType;
//
//        @Lob // For large text
//        @Column(name = "generated_code")
//        private String generatedCode;
//
//        @Type(type = "jsonb")  // Requires Hibernate and a JSONB type registration
//        @Column(name = "gemini_response", columnDefinition = "jsonb")
//        private String geminiResponse;
//
//        @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()")
//        private OffsetDateTime createdAt;
//
//        @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()")
//        private OffsetDateTime updatedAt;
//
//        @PreUpdate
//        public void preUpdate() {
//            updatedAt = OffsetDateTime.now();
//        }
//    }