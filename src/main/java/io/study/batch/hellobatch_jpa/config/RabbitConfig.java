package io.study.batch.hellobatch_jpa.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

@Profile("test-rabbitmq-postgresql")
@Configuration
public class RabbitConfig {
    private final MessageConverter messageConverter;
    private final ConnectionFactory connectionFactory;

    @Autowired
    public RabbitConfig(@Lazy @Qualifier("mqMessageConverter") MessageConverter messageConverter,
                        @Lazy ConnectionFactory connectionFactory){
        this.messageConverter = messageConverter;
        this.connectionFactory = connectionFactory;
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean(name = "mqMessageConverter")
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
