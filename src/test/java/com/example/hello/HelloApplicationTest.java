package com.example.hello;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Smoke test verifying that the Spring Boot application context loads
 * successfully and that the expected beans are wired in.
 */
@SpringBootTest
class HelloApplicationTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private HelloController helloController;

    @Test
    void applicationContextLoads() {
        assertThat(context).isNotNull();
    }

    @Test
    void helloControllerBeanIsRegistered() {
        assertThat(helloController).isNotNull();
        assertThat(context.getBean(HelloController.class)).isSameAs(helloController);
    }
}
