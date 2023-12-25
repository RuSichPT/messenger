package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password);

    void logout(String jwt);
}
