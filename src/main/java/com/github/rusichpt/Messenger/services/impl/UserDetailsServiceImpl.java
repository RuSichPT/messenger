package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.models.UserDetailsImpl;
import com.github.rusichpt.Messenger.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return toUserDetails(user);
        }
        throw new UsernameNotFoundException("User ‘" + username + "’ not found");
    }

    private UserDetails toUserDetails(User user) {
        return new UserDetailsImpl(user.getUsername(), user.getPassword());
    }
}
