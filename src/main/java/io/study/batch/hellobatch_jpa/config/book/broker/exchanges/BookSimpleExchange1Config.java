package io.study.batch.hellobatch_jpa.config.book.broker.exchanges;

import io.study.batch.hellobatch_jpa.config.book.broker.BookBrokerConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test-rabbitmq-postgresql")
@Configuration
public class BookSimpleExchange1Config {

    private static final BookBrokerConstants.ChannelType CHANNEL_TYPE = BookBrokerConstants.ChannelType.SIMPLE_BROADCAST;
    private static final String EXCHANGE_NAME = CHANNEL_TYPE.getExchangeName();

    // publisher/subscriber 큐 (1)
    @Bean(name = "sysoutPrintQueue")
    public Queue sysoutPrintQueue(){
        return new Queue("sysoutPrintQueue");
    }

    // publisher/subscriber 큐 (2)
    @Bean(name = "jpaWritingQueue")
    public Queue jpaWritingQueue(){
        return new Queue("jpaWritingQueue");
    }

    // Exchange Bean 인스턴스
    @Bean(name = "BOOK_SIMPLE_EXCHANGE_1")
    public FanoutExchange fanout(){
        return new FanoutExchange("BOOK_SIMPLE_EXCHANGE_1");
    }

    // sysoutPrintQueue 와 익스체인지 "BOOK_SIMPLE_EXCHANGE_1" 를 바인딩
    @Bean(name = "sysoutPrintBinding")
    public Binding sysoutPrintBinding(@Qualifier("sysoutPrintQueue") Queue queue,
                                      @Qualifier("BOOK_SIMPLE_EXCHANGE_1") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    // jpaWritingBinding 와 익스체인지 "BOOK_SIMPLE_EXCHANGE_1" 를 바인딩
    @Bean(name = "jpaWritingBinding")
    public Binding jpaWritingBinding(@Qualifier("jpaWritingQueue") Queue queue,
                                     @Qualifier("BOOK_SIMPLE_EXCHANGE_1") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
}
