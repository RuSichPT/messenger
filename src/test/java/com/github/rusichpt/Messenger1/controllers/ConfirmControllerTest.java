package com.github.rusichpt.Messenger1.controllers;

import com.github.rusichpt.Messenger1.controllers.config.ClientHelper;
import com.github.rusichpt.Messenger1.controllers.config.TestConfig;
import com.github.rusichpt.Messenger1.services.EmailService;
import com.github.rusichpt.Messenger1.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class ConfirmControllerTest {

    private final MockMvc mockMvc;
    private final ClientHelper clientHelper;
    @SpyBean
    private final UserService userService;

    @MockBean // заменяем реальный сервис, чтобы не спамить почту на несуществующий адрес.
    private final EmailService emailService;

    @Autowired
    public ConfirmControllerTest(MockMvc mockMvc, ClientHelper clientHelper, UserService userService, EmailService emailService) {
        this.mockMvc = mockMvc;
        this.clientHelper = clientHelper;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Test
    void sendEmailForConfirmation() throws Exception {
        mockMvc.perform(get("/api/v1/confirm/send-email")
                        .headers(clientHelper.signin()))
                .andExpect(status().isOk());
        // +2 потому что на старте выполняется создание 2-х пользователей, там происходит отправка
        verify(userService, times(1 + 2)).sendConfirmationCode(any());
    }
}