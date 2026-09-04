package com.microsservicos.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservicos.Service.KafkaService;

import lombok.AllArgsConstructor;

import com.example.DTO.PrecoDTO;

// API REST
@RestController
@AllArgsConstructor 
// Mapeia a classe através de um endpoint
@RequestMapping(value = "preco")
public class PrecoController {

    private KafkaService kafkaService;


    @PutMapping
    private ResponseEntity<Object> alteraPreco(@RequestBody PrecoDTO precoDTO){
        kafkaService.enviar(precoDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
