package com.microsservicos.demo.connections;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.stereotype.Component;

import com.example.constantes.RabbitMQConstantes;

import jakarta.annotation.PostConstruct;

@Component
public class RabbitMQConnection {
    
    private static final String NOME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;
    
    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila){
        return QueueBuilder
                .durable(nomeFila)
                .build();
    }

    private Queue filaEstoque(){
    //Quando uma mensagem da fila ESTOQUE for rejeitada sem requeue, publica novamente no amq.direct, agora com a routing key ESTOQUE.DLQ   
        return QueueBuilder
                .durable(RabbitMQConstantes.FILA_ESTOQUE)
                .deadLetterExchange(NOME_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQConstantes.ESTOQUE_DLQ)
                .build();
    }

    private DirectExchange trocaDireta(){
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona(){
        Queue filaEstoque = this.filaEstoque();
        Queue filaPreco = this.fila(RabbitMQConstantes.FILA_PRECO);
        Queue filaEstoqueDlq = this.fila(RabbitMQConstantes.ESTOQUE_DLQ);

        DirectExchange troca = this.trocaDireta();
        
        Binding ligacaoEstoque = this.relacionamento(filaEstoque, troca);
        Binding ligacaoPreco = this.relacionamento(filaPreco, troca);
        Binding ligacaoEstoqueDlq = this.relacionamento(filaEstoqueDlq, troca);

        this.amqpAdmin.declareQueue(filaEstoque);
        this.amqpAdmin.declareQueue(filaPreco);
        this.amqpAdmin.declareQueue(filaEstoqueDlq);

        this.amqpAdmin.declareExchange(troca);
        
        this.amqpAdmin.declareBinding(ligacaoEstoque);
        this.amqpAdmin.declareBinding(ligacaoPreco);
        this.amqpAdmin.declareBinding(ligacaoEstoqueDlq);
    }
}
