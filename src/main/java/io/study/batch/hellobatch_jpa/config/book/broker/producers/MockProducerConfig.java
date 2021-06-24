package io.study.batch.hellobatch_jpa.config.book.broker.producers;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// 추후 분리 적용.....
@Profile("test-rabbitmq-postgresql")
@Configuration
//@EnableScheduling
public class MockProducerConfig {
}
