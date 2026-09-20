package com.microsservicos.Controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservicos.Entity.Saga;
import com.microsservicos.Enum.SagaStatus;
import com.microsservicos.Repository.SagaRepository;
import com.microsservicos.Service.KafkaService;

import lombok.AllArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import com.example.DTO.PrecoDTO;

// API REST
@RestController
// Mapeia a classe através de um endpoint
@RequestMapping(value = "preco")
public class PrecoController {

    private KafkaService kafkaService;

    private ObjectMapper objectMapper;

    private SagaRepository sagaRepository;

    public PrecoController(KafkaService kafkaService, ObjectMapper objectMapper, SagaRepository sagaRepository) {
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
        this.sagaRepository = sagaRepository;
    }


    @PutMapping
    private ResponseEntity<Object> alteraPreco(@RequestBody PrecoDTO precoDTO){
        String json = objectMapper.writeValueAsString(precoDTO);
        // kafkaService.enviar(json);
        Saga saga = sagaRepository.save(new Saga(json, SagaStatus.OPEN, LocalDateTime.now()));
        kafkaService.enviar(saga.getId(), precoDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
