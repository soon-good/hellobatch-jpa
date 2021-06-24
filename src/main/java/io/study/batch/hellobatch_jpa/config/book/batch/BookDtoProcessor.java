package io.study.batch.hellobatch_jpa.config.book.batch;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookDtoProcessor implements ItemProcessor<BookDto, Book> {
    @Override
    public Book process(BookDto bookDto) throws Exception {
        return new Book(bookDto.getName(), bookDto.getPrice());
    }
}
