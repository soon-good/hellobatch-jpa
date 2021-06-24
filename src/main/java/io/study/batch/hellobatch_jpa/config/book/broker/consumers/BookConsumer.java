package io.study.batch.hellobatch_jpa.config.book.broker.consumers;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
import io.study.batch.hellobatch_jpa.shop.book.repository.BookRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class BookConsumer {

    @Autowired
    private BookRepository bookRepository;

    @Profile("test-rabbitmq-postgresql")
    @RabbitListener(queues = "sysoutPrintQueue")
    public void receive1(final BookDto message){
        System.out.println("[데이터받음 (sysoutPrintQueue)] <<< " + String.valueOf(message));
    }

    @Profile("test-rabbitmq-postgresql")
//    @RabbitListener(queues = "jpaWritingQueue")
    public void receive2(final BookDto message){
        System.out.println("[데이터받음 (jpaWritingBinding)] <<< " + String.valueOf(message));
        final Book newBook = new Book(message.getName(), message.getPrice());
        bookRepository.save(newBook);
    }
}
