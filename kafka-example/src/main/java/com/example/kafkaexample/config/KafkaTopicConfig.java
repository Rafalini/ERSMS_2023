package com.example.kafkaexample.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig  {
    @Bean
    public NewTopic ersmsTopic() {
        return TopicBuilder.name("ersms").build();
    }
}
