package com.event.driven.notifier.Service;

import java.time.Duration;
import java.util.Optional;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.event.driven.notifier.Domain.Notification;
import com.event.driven.notifier.Repository.NotificationRepository;
import com.event.driven.notifier.TestContainersConfiguration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Import(TestContainersConfiguration.class)
public class NotificationProducerTest {

    @Autowired 
    private NotificationProducer notificationProducer;

    @Autowired 
    private NotificationRepository notificationRepository;

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
