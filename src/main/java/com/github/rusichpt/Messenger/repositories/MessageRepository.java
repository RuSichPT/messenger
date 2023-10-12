package com.github.rusichpt.Messenger.repositories;

import com.github.rusichpt.Messenger.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByFromIdAndToId(Long fromId, Long toId, Pageable pageable);
}
