package com.event.driven.notifier.Configuration;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.event.driven.notifier.Domain.Notification;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Notification> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        configProps.put(JsonDeserializer.KEY_DEFAULT_TYPE, String.class);
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Notification.class);

        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.event.driven.notifier.Domain");

        return new DefaultKafkaConsumerFactory<>(configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(Notification.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Notification> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
