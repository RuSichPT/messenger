package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.configs.JwtUtils;
import com.github.rusichpt.Messenger.dto.AuthRequest;
import com.github.rusichpt.Messenger.dto.AuthResponse;
import com.github.rusichpt.Messenger.entities.BLackJwt;
import com.github.rusichpt.Messenger.entities.User;
import com.github.rusichpt.Messenger.services.BlackListService;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@Transactional
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final BlackListService blackListService;

    @PostMapping(path = "/signin")
    @Operation(summary = "Login to messenger")
    public AuthResponse authUser(@Valid @RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        User user = userService.findUserByUsername(request.getUsername());
        return new AuthResponse(jwtUtils.generateToken(user.getId().toString()));
    }

    @GetMapping(path = "/signout")
    @Operation(summary = "Logout of messenger")
    @SecurityRequirement(name = "Bearer Authentication")
    public void logoutUser(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        jwt = jwt.substring(7);
        Date expiration = jwtUtils.getExpiration(jwt);
        LocalDateTime expirationDateTime = LocalDateTime.ofInstant(
                expiration.toInstant(), ZoneId.systemDefault());
        blackListService.saveJwt(new BLackJwt(jwt, expirationDateTime));
    }
}
