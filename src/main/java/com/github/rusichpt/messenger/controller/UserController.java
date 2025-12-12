package com.github.rusichpt.messenger.controller;

import com.github.rusichpt.messenger.dto.UserCreateDTO;
import com.github.rusichpt.messenger.dto.UserDTO;
import com.github.rusichpt.messenger.dto.UserUpdateDTO;
import com.github.rusichpt.messenger.dto.UserUpdatePassDTO;
import com.github.rusichpt.messenger.entity.User;
import com.github.rusichpt.messenger.service.ChatService;
import com.github.rusichpt.messenger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User API")
public class UserController {
    private final UserService userService;
    private final ChatService chatService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    public UserDTO registerUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = userService.createUser(modelMapper.map(userCreateDTO, User.class));
        return modelMapper.map(user, UserDTO.class);
    }

    @Operation(summary = "Get user profile")
    @GetMapping(path = "/profile")
    public UserDTO getUserProfile(@AuthenticationPrincipal User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Operation(summary = "Update user profile")
    @PutMapping(path = "/profile")
    public UserDTO updateUserProfile(@AuthenticationPrincipal User user,
                                     @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        User updatedUser = userService.updateUser(user, userUpdateDTO);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Operation(summary = "Update user password")
    @PatchMapping(path = "/password")
    public void updateUserPass(@AuthenticationPrincipal User user, @Valid @RequestBody UserUpdatePassDTO passUpdateDTO) {
        userService.updateUserPass(user, passUpdateDTO.getPassword());
    }

    @Operation(summary = "Delete user")
    @DeleteMapping(path = "/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteUser(@AuthenticationPrincipal User user) {
        chatService.deleteChatsByUser(user);
        userService.deleteUser(user);
    }
}
