package com.example.hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Plain JUnit 5 tests for {@link HelloController} that exercise the controller
 * methods directly, without bootstrapping the Spring application context.
 */
class HelloControllerUnitTest {

    private HelloController controller;

    @BeforeEach
    void setUp() {
        controller = new HelloController();
    }

    @Test
    @DisplayName("root() returns service metadata map")
    void rootReturnsServiceMetadata() {
        Map<String, String> response = controller.root();

        assertThat(response)
                .containsEntry("service", "hello-spring-boot-microservice")
                .containsEntry("status", "UP")
                .hasSize(2);
    }

    @Test
    @DisplayName("hello() defaults to greeting 'world' when no name is supplied")
    void helloDefaultsToWorld() {
        Map<String, Object> response = controller.hello("world");

        assertThat(response)
                .containsEntry("message", "Hello, world!")
                .containsKey("timestamp");
    }

    @ParameterizedTest(name = "hello(\"{0}\") greets the supplied name")
    @ValueSource(strings = {"Mohit", "Devin", "Spring", "A", "very-long-name-with-dashes"})
    void helloGreetsProvidedName(String name) {
        Map<String, Object> response = controller.hello(name);

        assertThat(response).containsEntry("message", "Hello, " + name + "!");
    }

    @Test
    @DisplayName("hello() includes a parseable ISO-8601 timestamp not in the future")
    void helloIncludesParseableTimestamp() {
        Map<String, Object> response = controller.hello("world");

        Object timestamp = response.get("timestamp");
        assertThat(timestamp).isInstanceOf(String.class);

        Instant parsed = Instant.parse((String) timestamp);
        assertThat(parsed).isBeforeOrEqualTo(Instant.now());
    }

    @Test
    @DisplayName("hello() generates a fresh timestamp on each call")
    void helloGeneratesFreshTimestampPerCall() throws InterruptedException {
        Map<String, Object> first = controller.hello("world");
        Thread.sleep(5);
        Map<String, Object> second = controller.hello("world");

        assertThat(first.get("timestamp")).isNotEqualTo(second.get("timestamp"));
    }
}
