package com.github.rusichpt.messenger.service.impl;

import com.github.rusichpt.messenger.config.JwtUtils;
import com.github.rusichpt.messenger.dto.AuthResponse;
import com.github.rusichpt.messenger.entity.BLackJwt;
import com.github.rusichpt.messenger.entity.User;
import com.github.rusichpt.messenger.service.AuthService;
import com.github.rusichpt.messenger.service.BlackListService;
import com.github.rusichpt.messenger.service.UserService;
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
