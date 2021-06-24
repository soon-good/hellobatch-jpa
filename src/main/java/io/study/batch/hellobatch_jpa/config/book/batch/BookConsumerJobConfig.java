package io.study.batch.hellobatch_jpa.config.book.batch;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
import io.study.batch.hellobatch_jpa.shop.book.repository.BookRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
public class BookConsumerJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BookItemProcessor bookItemProcessor;
    private final EntityManagerFactory entityManagerFactory;
    private final RabbitTemplate rabbitTemplate;
    private final Queue jpaWritingQueue;
    private final BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    public BookConsumerJobConfig(JobBuilderFactory jobBuilderFactory,
                                 StepBuilderFactory stepBuilderFactory,
                                 BookItemProcessor processor,
                                 EntityManagerFactory entityManagerFactory,
                                 @Qualifier("receiveTemplateForSaving") RabbitTemplate rabbitTemplate,
                                 Queue jpaWritingQueue,
                                 BookRepository bookRepository){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.bookItemProcessor = processor;
        this.entityManagerFactory = entityManagerFactory;
        this.rabbitTemplate = rabbitTemplate;
        this.jpaWritingQueue = jpaWritingQueue;
        this.rabbitTemplate.setDefaultReceiveQueue("jpaWritingQueue");
        this.bookRepository = bookRepository;
    }

    @Bean(name = "bookMessageSaveJob")
    public Job bookMessageSaveJop(@Qualifier("bookMessagingStep") Step bookMessagingStep){
        return jobBuilderFactory.get("bookMessagingJob")
                .start(bookMessagingStep)
                .build();
    }

    @Bean(name = "bookMessagingStep")
    public Step bookMessagingStep(ItemReader<BookDto> bookMessageReader,
                                  ItemWriter<Book> bookMessageJpaWriter){
        return stepBuilderFactory.get("bookMessagingStep")
                .<BookDto, Book>chunk(47)
                .reader(bookMessageReader)
                .writer(bookMessageJpaWriter)
                .build();
    }

    @Bean(name = "bookMessageReader")
    public AmqpItemReader<BookDto> bookMessageReader(){
        AmqpItemReader<BookDto> bookAmqpItemReader = new AmqpItemReader<BookDto>(this.rabbitTemplate);
        return bookAmqpItemReader;
    }

    @Bean(name = "bookMessageJpaWriter")
    public ItemWriter<Book> bookMessageJpaWriter(){
        return ((List<? extends Book> books) -> {
            bookRepository.saveAll(books);
        });
    }
}
