package com.microsservicos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.EstoqueDTO;
import com.example.constantes.RabbitMQConstantes;
import com.microsservicos.Service.RabbitmqService;

// API REST
@RestController
// Mapeia a classe através de um endpoint
@RequestMapping(value = "estoque")
public class EstoqueController {
    
    @Autowired
    private RabbitmqService rabbitmqService;

    @PutMapping
    private ResponseEntity alteraEstoque(@RequestBody EstoqueDTO estoqueDto){
        this.rabbitmqService.enviaMensagem(RabbitMQConstantes.FILA_ESTOQUE, estoqueDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
