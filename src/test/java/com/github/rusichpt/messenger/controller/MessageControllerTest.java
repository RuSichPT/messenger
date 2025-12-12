package com.github.rusichpt.messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rusichpt.messenger.dto.AuthRequest;
import com.github.rusichpt.messenger.dto.AuthResponse;
import com.github.rusichpt.messenger.dto.MessageRequest;
import com.github.rusichpt.messenger.dto.StoryRequest;
import com.github.rusichpt.messenger.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MessageControllerTest {

    private final ObjectMapper objectMapper;

    @MockBean // заменяем реальный сервис, чтобы не спамить почту на несуществующий адрес.
    private final EmailService emailService;
    private final MockMvc mockMvc;

    @Autowired
    public MessageControllerTest(ObjectMapper objectMapper, EmailService emailService, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.mockMvc = mockMvc;
    }

    private HttpHeaders getAuthHeader() throws Exception {
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

    @Test
    void sendMessage() throws Exception {
        MessageRequest request = new MessageRequest("user1", "hello");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/message/send")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(getAuthHeader()))
                .andExpect(status().isOk());
    }

    @Test
    void sendMessageBlank() throws Exception {
        MessageRequest request = new MessageRequest("", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/message/send")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(getAuthHeader()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStory() throws Exception {
        StoryRequest request = new StoryRequest("user2", 0, 10);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/message/story")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(getAuthHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username1").exists());

    }

    @Test
    void getStoryBlank() throws Exception {
        StoryRequest request = new StoryRequest("", 0, 10);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/message/story")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(getAuthHeader()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.usernameFrom").exists());
    }
}