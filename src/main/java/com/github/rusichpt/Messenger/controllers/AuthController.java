package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.configs.JwtUtils;
import com.github.rusichpt.Messenger.dto.AuthRequest;
import com.github.rusichpt.Messenger.dto.AuthResponse;
import com.github.rusichpt.Messenger.dto.SignupRequest;
import com.github.rusichpt.Messenger.dto.SignupResponse;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.models.UserDetailsImpl;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse registerUser(@Valid @RequestBody SignupRequest request) {
        userService.createUser(request.toUser());
        return new SignupResponse("User created");
    }

    @PostMapping(path = "/signin")
    public AuthResponse authUser(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        User user = userService.findUserByUsername(request.getUsername());
        return new AuthResponse(jwtUtils.generateToken(user.getId().toString()));
    }

    @PostMapping(path = "/signout")
    public void logoutUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {

    }
}
