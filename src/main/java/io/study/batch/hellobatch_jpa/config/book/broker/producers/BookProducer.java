package io.study.batch.hellobatch_jpa.config.book.broker.producers;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
import io.study.batch.hellobatch_jpa.shop.book.repository.BookRepository;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("test-rabbitmq-postgresql")
@EnableScheduling
@Service
public class BookProducer{

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;
    private final BookRepository bookRepository;
    private final List<Book> books;

    @Autowired
    public BookProducer(RabbitTemplate rabbitTemplate,
                        @Qualifier("BOOK_SIMPLE_EXCHANGE_1")FanoutExchange fanoutExchange,
                        BookRepository bookRepository){
        this.rabbitTemplate = rabbitTemplate;
        this.fanoutExchange = fanoutExchange;
        this.bookRepository = bookRepository;
        this.books = bookRepository.findAll();
    }

    @Scheduled(initialDelay = 1000, fixedRate = 500)
    public void sendBookMessage(){
        System.out.println("======= [producer] =======");
        for(Book book : books){
            BookDto bookDto = new BookDto(book);
            rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", bookDto);
            System.out.println("[Sending] :: " + bookDto.toString());
        }
    }

}
