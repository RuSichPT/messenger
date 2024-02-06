package com.github.rusichpt.Messenger1.services;

import com.github.rusichpt.Messenger1.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password);

    void logout(String jwt);
}
