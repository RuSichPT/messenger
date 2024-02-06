package com.github.rusichpt.messenger.repositories;

import com.github.rusichpt.messenger.entities.Chat;
import com.github.rusichpt.messenger.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends ListCrudRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE" +
            " (c.user1 = ?1 or c.user1 = ?2) and (c.user2 =?1 or c.user2 =?2) and c.user1 <> c.user2")
    Optional<Chat> findByUsers(User user1, User user2);

    @Query("SELECT c FROM Chat c WHERE" +
            " (c.user1 = ?1 and c.user2 = ?1) ")
    Optional<Chat> findSelfChat(User user);

    void deleteByUser1OrUser2(User user1, User user2);
}
