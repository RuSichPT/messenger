package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.models.UserDetailsImpl;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User API")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<User> users() {
        return userService.findAllUsers();
    }

    @Operation(summary = "Update user profile", description = "Update user profile")
    @PutMapping(path = "/update/profile")
    public UserProfile updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserProfile profile) {
        return userService.updateUserProfileById(userDetails.getId(), profile);
    }

    @Operation(summary = "Update user password", description = "Update user password")
    @PatchMapping(path = "/update/password")
    public void updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody String password) {
        userService.updateUserPasswordById(userDetails.getId(), password);
    }

    @Operation(summary = "Delete user", description = "Delete user")
    @DeleteMapping(path = "/delete/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUserById(userDetails.getId());
    }

}
