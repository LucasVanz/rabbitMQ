package com.microsservicos.demo.connections;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration 
public class KafkaConnection {
    
    public static final String TOPICO_PRECO = "preco";

    @Bean 
    public NewTopic topicoPreco(){
        return TopicBuilder
                .name(TOPICO_PRECO)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
