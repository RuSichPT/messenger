package com.github.rusichpt.Messenger.repositories;

import com.github.rusichpt.Messenger.models.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    User findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

}
