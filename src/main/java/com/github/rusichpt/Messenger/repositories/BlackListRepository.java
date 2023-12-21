package com.github.rusichpt.Messenger.repositories;

import com.github.rusichpt.Messenger.entities.BLackJwt;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BlackListRepository extends ListCrudRepository<BLackJwt, Long> {
    Optional<BLackJwt> findByToken(String token);

    void deleteAllByExpirationBefore(LocalDateTime dateTime);
}
