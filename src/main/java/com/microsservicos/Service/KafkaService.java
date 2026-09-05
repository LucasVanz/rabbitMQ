package com.microsservicos.Service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.microsservicos.demo.connections.KafkaConnection;



@Service
public class KafkaService {
    
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(String json) {
        kafkaTemplate.send(KafkaConnection.TOPICO_PRECO, json);
    }
}
