package com.github.rusichpt.Messenger.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rusichpt.Messenger.dto.AuthRequest;
import com.github.rusichpt.Messenger.dto.AuthResponse;
import com.github.rusichpt.Messenger.dto.SignupRequest;
import com.github.rusichpt.Messenger.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {
    private final ObjectMapper objectMapper;
    @MockBean // заменяем реальный сервис, чтобы не спамить почту на несуществующий адрес.
    private final EmailService emailService;
    private final MockMvc mockMvc;

    @Autowired
    AuthControllerTest(ObjectMapper objectMapper, EmailService emailService, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.mockMvc = mockMvc;
    }

    @Test
    void registerUser() throws Exception {
        SignupRequest request = new SignupRequest("user3", "123",
                "user3@mail.ru", "Name", "Surname");

        mockMvc.perform(post("/api/v1/auth/signup")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void registerExistedUser() throws Exception {
        SignupRequest request = new SignupRequest("user1", "123",
                "user1@mail.ru", "Name", "Surname");

        mockMvc.perform(post("/api/v1/auth/signup")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authUser() throws Exception {
        AuthRequest request = new AuthRequest("user1", "123");

        mockMvc.perform(post("/api/v1/auth/signin")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void unauthorizedUser() throws Exception {
        AuthRequest request = new AuthRequest("user4", "123");

        mockMvc.perform(post("/api/v1/auth/signin")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void logoutUser() throws Exception {
        AuthRequest request = new AuthRequest("user1", "123");
        AuthResponse response = login(request);
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(response.getToken());
        mockMvc.perform(get("/api/v1/auth/signout")
                        .headers(header))
                .andExpect(status().isOk());
    }

    private AuthResponse login(AuthRequest request) throws Exception {
        String contentAsString = mockMvc.perform(post("/api/v1/auth/signin")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(new ByteArrayInputStream(contentAsString.getBytes()), AuthResponse.class);
    }

}