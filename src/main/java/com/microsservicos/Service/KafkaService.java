package com.microsservicos.Service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.microsservicos.avro.Preco;
import com.microsservicos.demo.connections.KafkaConnection;
import com.example.DTO.PrecoDTO;


@Service
public class KafkaService {
    
    private final KafkaTemplate<String, Preco> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, Preco> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

   // public void enviar(String json) {
   //     kafkaTemplate.send(KafkaConnection.TOPICO_PRECO, json);
   // }
   public void enviar(PrecoDTO precoDTO) {
       Preco precoAvro = Preco.newBuilder()
                .setNomeProduto(precoDTO.nomeProduto)
                .setPreco(precoDTO.preco)
                .build();    
       kafkaTemplate.send(KafkaConnection.TOPICO_PRECO, precoAvro);
   }
}

