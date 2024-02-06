package com.github.rusichpt.messenger.services;

import com.github.rusichpt.messenger.entities.BLackJwt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BlackListService {
    BLackJwt saveJwt(BLackJwt jwt);

    void deleteAllJwtByExpirationBefore(LocalDateTime dateTime);

    Optional<BLackJwt> findJwt(String jwt);

    List<BLackJwt> findAllJwt();
}
