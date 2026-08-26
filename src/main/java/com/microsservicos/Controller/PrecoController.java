package com.microsservicos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservicos.Service.RabbitmqService;
import com.example.DTO.PrecoDTO;
import com.example.constantes.RabbitMQConstantes;

// API REST
@RestController
// Mapeia a classe através de um endpoint
@RequestMapping(value = "preco")
public class PrecoController {
    
    @Autowired
    private RabbitmqService rabbitmqService;

    @PutMapping
    private ResponseEntity alteraPreco(@RequestBody PrecoDTO precoDTO){
        rabbitmqService.enviaMensagem(RabbitMQConstantes.FILA_PRECO, precoDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
