package com.github.rusichpt.messenger.controllers;

import com.github.rusichpt.messenger.dto.AuthRequest;
import com.github.rusichpt.messenger.dto.AuthResponse;
import com.github.rusichpt.messenger.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@Transactional
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/signin")
    @Operation(summary = "Login to messenger")
    public AuthResponse authUser(@Valid @RequestBody AuthRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }

    @GetMapping(path = "/signout")
    @Operation(summary = "Logout of messenger")
    @SecurityRequirement(name = "Bearer Authentication")
    public void logoutUser(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        authService.logout(jwt);
    }
}
