package io.study.batch.hellobatch_jpa.config.book.batch;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
import io.study.batch.hellobatch_jpa.shop.book.repository.BookRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
public class BookConsumerJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BookDtoProcessor dtoProcessor;
    private final EntityManagerFactory entityManagerFactory;
    private final RabbitTemplate rabbitTemplate;
    private final BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    public BookConsumerJobConfig(JobBuilderFactory jobBuilderFactory,
                                 StepBuilderFactory stepBuilderFactory,
                                 BookDtoProcessor dtoProcessor,
                                 EntityManagerFactory entityManagerFactory,
                                 @Qualifier("receiveTemplateForSaving") RabbitTemplate rabbitTemplate,
                                 BookRepository bookRepository){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dtoProcessor = dtoProcessor;
        this.entityManagerFactory = entityManagerFactory;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setDefaultReceiveQueue("jpaWritingQueue");
        this.bookRepository = bookRepository;
    }

    @Bean(name = "bookMessageSaveJob")
    public Job bookMessageSaveJop(@Qualifier("bookMessagingStep") Step bookMessagingStep){
        return jobBuilderFactory.get("bookMessagingJob")
                .start(bookMessagingStep)
                .build();
    }

    @JobScope
    @Bean(name = "bookMessagingStep")
    public Step bookMessagingStep(ItemReader<BookDto> bookMessageReader,
                                  ItemWriter<Book> bookMessageJpaWriter){
        return stepBuilderFactory.get("bookMessagingStep")
                .<BookDto, Book>chunk(10)
                .reader(bookMessageReader)
                .processor(dtoProcessor)
                .writer(bookMessageJpaWriter)
                .build();
    }

    @StepScope
    @Bean(name = "bookMessageReader")
    public AmqpItemReader<BookDto> bookMessageReader(){
        AmqpItemReader<BookDto> bookAmqpItemReader = new AmqpItemReader<BookDto>(this.rabbitTemplate);
        return bookAmqpItemReader;
    }

    @StepScope
    @Bean(name = "bookMessageJpaWriter")
    public JpaItemWriter<Book> bookMessageJpaWriter(){
        System.out.println("[데이터 write]");
        JpaItemWriter<Book> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
