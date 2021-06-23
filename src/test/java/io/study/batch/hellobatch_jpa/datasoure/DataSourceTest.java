package io.study.batch.hellobatch_jpa.datasoure;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import io.study.batch.hellobatch_jpa.shop.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("test-rabbitmq-postgresql")
@SpringBootTest
public class DataSourceTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSelectAll(){
        List<Book> all = bookRepository.findAll();
        System.out.println(all);
    }
}
