package com.microsservicos.Service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.DTO.PrecoDTO;
import com.microsservicos.demo.connections.KafkaConnection;

import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class KafkaService {
    
    private final KafkaTemplate<String, PrecoDTO> kafkaTemplate;

    public void enviar(PrecoDTO precoDTO) {
        kafkaTemplate.send(KafkaConnection.TOPICO_PRECO, precoDTO);
    }
}
