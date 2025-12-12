package com.github.rusichpt.messenger.repository;

import com.github.rusichpt.messenger.entity.Chat;
import com.github.rusichpt.messenger.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByChat(Chat chat, Pageable pageable);
}
