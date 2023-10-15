package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.models.BLackJwt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BlackListService {
    BLackJwt saveJwt(BLackJwt jwt);
    void deleteAllJwtByExpirationBefore(LocalDateTime dateTime);

    Optional<BLackJwt> findJwt(String jwt);

    List<BLackJwt> findAllJwt();
}
