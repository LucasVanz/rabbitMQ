package com.microsservicos.Service;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.DTO.PrecoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsservicos.Entity.Saga;
import com.microsservicos.Repository.SagaRepository;
import com.microsservicos.avro.Preco;
import com.microsservicos.demo.connections.KafkaConnection;

@Service
public class SagaResyncService {

    private final SagaRepository sagaRepository;

    private final KafkaTemplate<String, Preco> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public SagaResyncService(SagaRepository sagaRepository, KafkaTemplate<String, Preco> kafkaTemplate) {
        this.sagaRepository = sagaRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Scheduled(fixedDelay = 5000)
    public void resync() {
        LocalDateTime limite = LocalDateTime.now().minusSeconds(10);
        for (Saga saga : sagaRepository.listByStatusAndCreatedAt(limite)) {
            try {
                PrecoDTO precoDTO = objectMapper.readValue(saga.getEntidade(), PrecoDTO.class);
                Preco precoAvro = Preco.newBuilder()
                        .setSagaId(saga.getId())
                        .setNomeProduto(precoDTO.nomeProduto)
                        .setPreco(precoDTO.preco)
                        .build();
                kafkaTemplate.send(KafkaConnection.TOPICO_PRECO, saga.getId().toString(), precoAvro)
                        .whenComplete((resultado, erro) -> {
                            if (erro != null) {
                                System.out.println(
                                        "Falha ao reenviar Saga " + saga.getId()
                                                + ": " + erro.getMessage());
                            } else {
                                System.out.println(
                                        "Saga " + saga.getId()
                                                + " reenviada para o Kafka");
                            }
                        });
            } catch (Exception e) {
                System.out.println("Erro ao reenviar saga " + saga.getId() + ": " + e.getMessage());
            }

        }
    }

}
