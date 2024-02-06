package com.github.rusichpt.messenger.services.impl;

import com.github.rusichpt.messenger.configs.JwtUtils;
import com.github.rusichpt.messenger.dto.AuthResponse;
import com.github.rusichpt.messenger.entities.BLackJwt;
import com.github.rusichpt.messenger.entities.User;
import com.github.rusichpt.messenger.services.AuthService;
import com.github.rusichpt.messenger.services.BlackListService;
import com.github.rusichpt.messenger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final BlackListService blackListService;

    @Override
    public AuthResponse login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userService.findUserByUsername(username);

        return new AuthResponse(jwtUtils.generateToken(user.getId().toString()));
    }

    @Override
    public void logout(String jwt) {
        jwt = jwt.substring(7);
        Date expiration = jwtUtils.getExpiration(jwt);
        LocalDateTime expirationDateTime = LocalDateTime.ofInstant(
                expiration.toInstant(), ZoneId.systemDefault());
        blackListService.saveJwt(new BLackJwt(jwt, expirationDateTime));
    }
}
