package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.entities.User;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Confirmation")
public class ConfirmController {

    private final UserService userService;

    @Operation(summary = "Confirm the user's email address")
    @GetMapping(path = "/confirm/{id}/{code}")
    public void confirmEmail(@PathVariable Long id, @PathVariable String code) {
        User user = userService.findUserById(id);
        if (user.getConfirmationCode().equals(code)) {
            user.setEmailConfirmed(true);
            userService.updateUser(user);
            log.info("User email confirmed");
        }
    }
}
