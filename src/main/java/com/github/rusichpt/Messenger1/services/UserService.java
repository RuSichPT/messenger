package com.github.rusichpt.Messenger1.services;

import com.github.rusichpt.Messenger1.dto.UserUpdateDTO;
import com.github.rusichpt.Messenger1.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserById(Long id) throws UsernameNotFoundException;

    User createUser(User user);

    User findUserById(Long id);

    User findUserByUsername(String username);

    List<User> findAllUsers();

    User updateUser(User user, UserUpdateDTO userUpdateDTO);

    User updateUser(User user);

    User updateUserPass(User user, String password);

    void deleteUser(User User);

    void sendConfirmationCode(User user);

    void confirmEmail(Long userId, String code);

}
