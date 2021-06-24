package io.study.batch.hellobatch_jpa.config.book.batch;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookItemProcessor implements ItemProcessor<Book, Book> {
    @Override
    public Book process(Book book) throws Exception {
        book.setPrice(book.getPrice() * 10);
        return book;
    }
}
