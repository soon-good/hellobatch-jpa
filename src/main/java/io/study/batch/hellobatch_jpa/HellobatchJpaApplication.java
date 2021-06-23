package io.study.batch.hellobatch_jpa;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Profile("test-rabbitmq-postgresql")
@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class HellobatchJpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(HellobatchJpaApplication.class, args);
	}

}
