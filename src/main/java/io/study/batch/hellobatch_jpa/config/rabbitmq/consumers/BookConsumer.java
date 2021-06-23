package io.study.batch.hellobatch_jpa.config.rabbitmq.consumers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test-rabbitmq-postgresql")
@Service
public class BookConsumer {

    @RabbitListener(queues = "sysoutPrintQueue")
    public void receive1(final Message message){
        System.out.println("[큐 : sysoutPrintQueue] " + String.valueOf(message));
    }

    @RabbitListener(queues = "jpaWritingBinding")
    public void receive2(final Message message){
        System.out.println("[큐 : jpaWritingBinding] " + String.valueOf(message));
        // batch작업작업
        // AmqpReader 어떻게해야 할까??
    }
}
