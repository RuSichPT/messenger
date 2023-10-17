package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.dto.PassRequest;
import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.services.ChatService;
import com.github.rusichpt.Messenger.services.EmailService;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User API")
public class UserController {
    private final UserService userService;
    private final ChatService chatService;
    private final EmailService emailService;

    @Operation(summary = "Get user profile")
    @GetMapping(path = "/profile")
    public UserProfile getProfile(@AuthenticationPrincipal User user) {
        return userService.findUserById(user.getId()).getProfile();
    }

    @Operation(summary = "Update user profile")
    @PutMapping(path = "/update/profile")
    public UserProfile updateUserProfile(@AuthenticationPrincipal User user, @Valid @RequestBody UserProfile profile) {
        userService.checkUniqueEmailAndUsername(profile.getUsername(), profile.getEmail());
        String oldEmail = user.getEmail();
        user.setProfile(profile);
        if (!oldEmail.equals(profile.getEmail())) {
            user.setEmailConfirmed(false);
            user.setConfirmationCode(UUID.randomUUID().toString());
            emailService.sendConfirmationCode(user);
        }
        return userService.updateUser(user).getProfile();
    }

    @Operation(summary = "Update user password")
    @PatchMapping(path = "/update/password")
    public void updateUserPass(@AuthenticationPrincipal User user, @Valid @RequestBody PassRequest request) {
        userService.updateUserPass(user, request.getPassword());
    }

    @Operation(summary = "Delete user")
    @DeleteMapping(path = "/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteUser(@AuthenticationPrincipal User user) {
        chatService.deleteChatsByUser(user);
        userService.deleteUser(user);
    }

    @Operation(summary = "Send an email to confirm user email address")
    @GetMapping(path = "/send-email")
    public void sendEmailForConfirmation(@AuthenticationPrincipal User user) {
        emailService.sendConfirmationCode(user);
    }
}
