package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users",
        produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping(path = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
