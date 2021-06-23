package io.study.batch.hellobatch_jpa.datasoure;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import io.study.batch.hellobatch_jpa.shop.book.BookService;
import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
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

    @Autowired
    private BookService bookService;

    @Test
    public void testSelectAll(){
        List<Book> all = bookRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void testSelectAllDto(){
        List<BookDto> bookDtos = bookService.listAllBook();
        System.out.println(bookDtos);
    }
}
