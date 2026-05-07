package com.example.hello;

import java.time.Instant;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, String> root() {
        return Map.of(
                "service", "hello-spring-boot-microservice",
                "status", "UP"
        );
    }

    @GetMapping("/hello")
    public Map<String, Object> hello(@RequestParam(defaultValue = "world") String name) {
        return Map.of(
                "message", "Hello, " + name + "!",
                "timestamp", Instant.now().toString()
        );
    }
}
