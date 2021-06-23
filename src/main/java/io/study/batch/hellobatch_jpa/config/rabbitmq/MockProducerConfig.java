package io.study.batch.hellobatch_jpa.config.rabbitmq;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Profile("test-rabbitmq-postgresql")
@Configuration
@EnableScheduling
public class MockProducerConfig {
}
