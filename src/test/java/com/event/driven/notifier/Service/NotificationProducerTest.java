package com.event.driven.notifier.Service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import com.event.driven.notifier.Repository.NotificationRepository;
import com.event.driven.notifier.Domain.Notification;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.Duration;
import static java.util.concurrent.TimeUnit.SECONDS;
import org.awaitility.Awaitility;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@Testcontainers
public class NotificationProducerTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
        DockerImageName.parse("apache/kafka")
    );

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
        DockerImageName.parse("postgres:17")
    );

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        // Kafka configuration
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
        
        // PostgreSQL configuration
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired 
    private NotificationProducer notificationProducer;

    @Autowired 
    private NotificationRepository notificationRepository;

    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;

    @Test
    void whenSentEvent_ConsumerReceivesIt() {
        Notification notification = Notification.builder()
            .message("Message")
            .build();

        notificationProducer.sendNotification(notification);   
        
    Awaitility.await()
      .pollInterval(Duration.ofSeconds(3))
      .atMost(90, SECONDS)
      .untilAsserted(() -> {
        Optional<Notification> optionalNotification = notificationRepository.findByNotificationID(
          notification.getNotificationID()
        );
        assertThat(optionalNotification).isPresent();
        assertThat(optionalNotification.get().getNotificationID()).isEqualTo(notification.getNotificationID());
      });
    }
}
