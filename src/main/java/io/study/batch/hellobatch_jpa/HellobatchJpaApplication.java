package io.study.batch.hellobatch_jpa;

import io.study.batch.hellobatch_jpa.shop.book.BookService;
import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.function.Consumer;

@EnableBatchProcessing
@SpringBootApplication
public class HellobatchJpaApplication {

	@Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(HellobatchJpaApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationLoaded(){
		List<BookDto> bookList = bookService.listAllBook();

		Consumer<List<BookDto>> whileConsumer = (list) -> {
//			while(true){
				for(BookDto book : list){
					System.out.println(book);
				}
//			}
		};

		whileConsumer.accept(bookList);
	}
}
