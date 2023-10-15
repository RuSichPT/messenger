package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.configs.JwtUtils;
import com.github.rusichpt.Messenger.dto.AuthRequest;
import com.github.rusichpt.Messenger.dto.AuthResponse;
import com.github.rusichpt.Messenger.dto.SignupRequest;
import com.github.rusichpt.Messenger.dto.SignupResponse;
import com.github.rusichpt.Messenger.models.BLackJwt;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.services.BlackListService;
import com.github.rusichpt.Messenger.services.EmailService;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Authentication API")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final BlackListService blackListService;

    private final EmailService emailService;

    @Value("${host.url}")
    private String host;

    @Operation(summary = "Register a new user")
    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse registerUser(@Valid @RequestBody SignupRequest request) {
        User user = userService.createUser(request.toUser());
        String message = String.format("Hello, %s! \n" +
                "Welcome to Messenger. Please, visit next link:" + host + "/confirm/%s/%s", user.getUsername(), user.getId(), user.getConfirmationCode());
        emailService.sendSimpleEmail(user.getEmail(), "Confirmation code", message);
        return new SignupResponse("User created");
    }

    @PostMapping(path = "/signin")
    @Operation(summary = "Login to messenger")
    public AuthResponse authUser(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        User user = userService.findUserByUsername(request.getUsername());
        return new AuthResponse(jwtUtils.generateToken(user.getId().toString()));
    }

    @Operation(summary = "Logout of messenger")
    @GetMapping(path = "/signout")
    @SecurityRequirement(name = "Bearer Authentication")
    public void logoutUser(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        jwt = jwt.substring(7);
        Date expiration = jwtUtils.getExpiration(jwt);
        LocalDateTime expirationDateTime = LocalDateTime.ofInstant(
                expiration.toInstant(), ZoneId.systemDefault());
        blackListService.saveJwt(new BLackJwt(jwt, expirationDateTime));
    }
}
