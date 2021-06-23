package io.study.batch.hellobatch_jpa.shop.book;

import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
import io.study.batch.hellobatch_jpa.shop.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository repository;

    @Override
    public List<BookDto> listAllBook() {
        List<Book> list = repository.findAll();

        List<BookDto> result = list.stream()
                .map(BookDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
