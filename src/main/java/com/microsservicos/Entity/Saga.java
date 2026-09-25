package com.microsservicos.Entity;

import java.time.LocalDateTime;

import com.microsservicos.Enum.SagaStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter 
@Setter 
public class Saga {

    
    public Saga(String entidade, SagaStatus status, LocalDateTime createdAt) {
        this.entidade = entidade;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    public Saga() {
    }
    

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entidade;
    @Enumerated (EnumType.STRING)
    private SagaStatus status;
    @Column (name = "created_at")
    private LocalDateTime createdAt;

    
}
