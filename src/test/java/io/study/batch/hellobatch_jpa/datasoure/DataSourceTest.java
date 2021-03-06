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

@Profile("test-datasource")
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
        List<BookDto> dtoList = bookService.listAllBook();
        System.out.println(dtoList);
    }

    @Test
    public void testInsertDto(){
        Book book1 = new Book("미국 주식에 미치다", 100);
        bookRepository.save(book1);
    }
}
