package com.github.rusichpt.messenger.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rusichpt.messenger.dto.AuthRequest;
import com.github.rusichpt.messenger.dto.AuthResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientHelper {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public ClientHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public HttpHeaders signin() throws Exception {
        AuthRequest request = new AuthRequest("user1", "123");
        String contentAsString = mockMvc.perform(post("/api/v1/auth/signin")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        AuthResponse response = objectMapper.readValue(new ByteArrayInputStream(contentAsString.getBytes()), AuthResponse.class);
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(response.getToken());

        return header;
    }
}
