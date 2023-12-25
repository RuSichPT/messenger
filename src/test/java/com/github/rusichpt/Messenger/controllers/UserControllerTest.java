package com.github.rusichpt.Messenger.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rusichpt.Messenger.controllers.config.ClientHelper;
import com.github.rusichpt.Messenger.controllers.config.TestConfig;
import com.github.rusichpt.Messenger.dto.UserUpdateDTO;
import com.github.rusichpt.Messenger.dto.UserUpdatePassDTO;
import com.github.rusichpt.Messenger.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Import(TestConfig.class)
class UserControllerTest {
    private final ObjectMapper objectMapper;
    @MockBean // заменяем реальный сервис, чтобы не спамить почту на несуществующий адрес.
    private final EmailService emailService;
    private final MockMvc mockMvc;
    private final ClientHelper clientHelper;

    @Autowired
    public UserControllerTest(ObjectMapper objectMapper, EmailService emailService, MockMvc mockMvc, ClientHelper clientHelper) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.mockMvc = mockMvc;
        this.clientHelper = clientHelper;
    }

    @Test
    void getProfile() throws Exception {
        mockMvc.perform(get("/api/v1/users/profile")
                        .headers(clientHelper.signin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    void updateUserProfile() throws Exception {
        UserUpdateDTO profile = new UserUpdateDTO("username", "test@mail.ru", "Name", "Surname");
        mockMvc.perform(put("/api/v1/users/profile")
                        .content(objectMapper.writeValueAsString(profile))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(clientHelper.signin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(profile.getUsername()));
    }

    @Test
    void updateUserProfileNotUniqueUsername() throws Exception {
        UserUpdateDTO profile = new UserUpdateDTO("user1", "test@mail.ru", "Name", "Surname");
        mockMvc.perform(put("/api/v1/users/profile")
                        .content(objectMapper.writeValueAsString(profile))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(clientHelper.signin()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUserBlankPass() throws Exception {
        UserUpdatePassDTO request = new UserUpdatePassDTO("");
        mockMvc.perform(patch("/api/v1/users/password")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(clientHelper.signin()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/delete")
                        .headers(clientHelper.signin()))
                .andExpect(status().isNoContent());
    }

}