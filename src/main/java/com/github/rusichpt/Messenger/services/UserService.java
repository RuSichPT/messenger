package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByUsername(String username);

    List<User> findAllUsers();

    UserProfile updateUserProfileByUsername(String username, UserProfile profile);

    void updateUserPasswordByUsername(String username, @NotNull String password);


    void deleteUserByUsername(String username);
}
