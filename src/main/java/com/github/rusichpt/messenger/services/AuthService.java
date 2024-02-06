package com.github.rusichpt.messenger.services;

import com.github.rusichpt.messenger.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password);

    void logout(String jwt);
}
