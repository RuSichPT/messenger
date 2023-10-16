package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.services.EmailService;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User API")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    @Value("${host.url}")
    private String host;

    @Operation(summary = "Update user profile")
    @PutMapping(path = "/update/profile")
    public UserProfile updateUser(@AuthenticationPrincipal User user, @RequestBody UserProfile profile) {
        return userService.updateUserProfileById(user.getId(), profile);
    }

    @Operation(summary = "Update user password")
    @PatchMapping(path = "/update/password")
    public void updateUser(@AuthenticationPrincipal User user, @NotNull @RequestBody String password) {
        userService.updateUserPasswordById(user.getId(), password);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping(path = "/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUserById(user.getId());
    }

    @Operation(summary = "Send an email to confirm user email address")
    @GetMapping(path = "/send-email")
    public void sendEmailForConfirmation(@AuthenticationPrincipal User user) {
        String message = String.format("Hello, %s! \n" +
                "Welcome to Messenger. Please, visit next link:" + host + "/confirm/%s/%s", user.getUsername(), user.getId(), user.getConfirmationCode());
        emailService.sendSimpleEmail(user.getEmail(), "Confirmation code", message);
    }
}
