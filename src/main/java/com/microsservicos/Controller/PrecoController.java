package com.microsservicos.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public PrecoController(KafkaService kafkaService, ObjectMapper objectMapper) {
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
    }


    @PutMapping
    private ResponseEntity<Object> alteraPreco(@RequestBody PrecoDTO precoDTO){
        String json = objectMapper.writeValueAsString(precoDTO);
        kafkaService.enviar(json);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
