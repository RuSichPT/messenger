package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User findUserById(Long id);

    User findUserByUsername(String username);

    List<User> findAllUsers();

    User updateUser(User user);

    UserProfile updateUserProfileById(Long id, UserProfile profile);

    void updateUserPasswordById(Long id, @NotNull String password);


    void deleteUserById(Long id);
}
