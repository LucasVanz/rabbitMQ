package com.microsservicos.Entity;

import java.time.LocalDateTime;

import com.microsservicos.Enum.SagaStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Saga {
    @Id 
    private Long id;
    private String entidade;
    @Enumerated (EnumType.STRING)
    private SagaStatus status;
    @Column (name = "created_at")
    private LocalDateTime createdAt;

    
}
