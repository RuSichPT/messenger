package com.github.rusichpt.messenger.services.impl;

import com.github.rusichpt.messenger.entities.BLackJwt;
import com.github.rusichpt.messenger.repositories.BlackListRepository;
import com.github.rusichpt.messenger.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BLackListServiceImpl implements BlackListService {
    private final BlackListRepository repository;

    @Override
    public BLackJwt saveJwt(BLackJwt jwt) {
        return repository.save(jwt);
    }

    @Override
    public void deleteAllJwtByExpirationBefore(LocalDateTime dateTime) {
        repository.deleteAllByExpirationBefore(dateTime);
    }

    @Override
    public Optional<BLackJwt> findJwt(String jwt) {
        return repository.findByToken(jwt);
    }

    @Override
    public List<BLackJwt> findAllJwt() {
        return repository.findAll();
    }
}
