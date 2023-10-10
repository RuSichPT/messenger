package com.github.rusichpt.Messenger.repositories;

import com.github.rusichpt.Messenger.models.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    User findByUsernameOrEmail(String username,String email);
    User findByUsername(String username);
}
