package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.models.UserDetailsImpl;
import com.github.rusichpt.Messenger.services.UserService;
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
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<User> users() {
        return userService.findAllUsers();
    }

    @PutMapping(path = "/update/profile")
    public UserProfile updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserProfile profile) {
        return userService.updateUserProfileByUsername(userDetails.getUsername(), profile);
    }

    @PatchMapping(path = "/update/password")
    public void updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody String password) {
        userService.updateUserPasswordByUsername(userDetails.getUsername(), password);
    }

    @DeleteMapping(path = "/delete/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUserByUsername(userDetails.getUsername());
    }

}
