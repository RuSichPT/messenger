package com.github.rusichpt.messenger.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

@TestConfiguration
public class TestConfig {

    @Bean
    public ClientHelper clientHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        return new ClientHelper(mockMvc, objectMapper);
    }
}
