package com.example.hello;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    private static final String ISO_8601_PATTERN =
            "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?Z$";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void rootReturnsServiceMetadata() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.service").value("hello-spring-boot-microservice"))
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void helloDefaultsToWorld() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Hello, world!"))
                .andExpect(content().string(containsString("Hello, world!")));
    }

    @Test
    void helloAcceptsNameParam() throws Exception {
        mockMvc.perform(get("/hello").param("name", "Mohit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Mohit!"))
                .andExpect(content().string(containsString("Hello, Mohit!")));
    }

    @Test
    void helloFallsBackToDefaultWhenNameIsEmpty() throws Exception {
        // Spring applies @RequestParam(defaultValue) when the param is missing
        // OR when it's present but empty, so an empty `name` resolves to "world".
        mockMvc.perform(get("/hello").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, world!"));
    }

    @Test
    void helloHandlesSpecialCharactersInName() throws Exception {
        mockMvc.perform(get("/hello").param("name", "Jose Maria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Jose Maria!"));
    }

    @Test
    void helloHandlesUnicodeName() throws Exception {
        mockMvc.perform(get("/hello").param("name", "世界"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, 世界!"));
    }

    @Test
    void helloIncludesValidIso8601Timestamp() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timestamp").isString())
                .andExpect(jsonPath("$.timestamp", matchesPattern(ISO_8601_PATTERN)));
    }

    @Test
    void helloRejectsPostMethod() throws Exception {
        mockMvc.perform(post("/hello"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void unknownPathReturnsNotFound() throws Exception {
        mockMvc.perform(get("/does-not-exist"))
                .andExpect(status().isNotFound());
    }

    @Test
    void actuatorHealthReportsUp() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
