package com.github.rusichpt.Messenger1.controllers;

import com.github.rusichpt.Messenger1.entities.User;
import com.github.rusichpt.Messenger1.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Confirmation")
@RequestMapping(path = "/api/v1/confirm",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ConfirmController {

    private final UserService userService;

    @Operation(summary = "Confirm the user's email address")
    @GetMapping(path = "/{id}/{code}")
    public void confirmEmail(@PathVariable Long id, @PathVariable String code) {
        userService.confirmEmail(id, code);
    }

    @Operation(summary = "Send an email to confirm user email address")
    @GetMapping(path = "/send-email")
    public void sendEmailForConfirmation(@AuthenticationPrincipal User user) {
        userService.sendConfirmationCode(user);
    }
}
