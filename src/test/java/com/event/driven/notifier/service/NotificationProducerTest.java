package com.event.driven.notifier.service;


import java.time.Duration;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import com.event.driven.notifier.TestContainersConfiguration;
import com.event.driven.notifier.domain.entities.Notification;
import com.event.driven.notifier.domain.entities.User;
import com.event.driven.notifier.repository.NotificationRepository;
import com.event.driven.notifier.repository.UserRepository;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestContainersConfiguration.class)
class NotificationProducerTest {

    @Autowired
    private NotificationProducer notificationProducer;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSentEvent_ConsumerReceivesIt() {
        User user = User.builder()
                .email("test@example.com")
                .name("Test User")
                .password("password")
                .build();
        User savedUser = userRepository.saveAndFlush(user);

        Notification notification = Notification.builder()
                .message("Message")
                .userID(savedUser.getId())
                .build();

        notificationProducer.sendNotification(notification);

        Awaitility.await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(90, SECONDS)
                .untilAsserted(() -> {
                    Optional<Notification> optionalNotification = notificationRepository.findByUserID(
                            notification.getUserID()
                    );
                    assertThat(optionalNotification).isPresent();
                    assertThat(optionalNotification.get().getUserID()).isEqualTo(savedUser.getId());
                });
    }
}
