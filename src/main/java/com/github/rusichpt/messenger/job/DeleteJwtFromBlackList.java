package com.github.rusichpt.messenger.job;

import com.github.rusichpt.messenger.service.BlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteJwtFromBlackList {
    private final BlackListService service;

    @Scheduled(fixedRateString = "${job.checkBlackListTimeMs}")
    public void findJwtForDeletion() {
        LocalDateTime start = LocalDateTime.now();

        log.info("Find jwt for deletion job started.");

        service.deleteAllJwtByExpirationBefore(start);

        LocalDateTime end = LocalDateTime.now();

        log.info("Find jwt for deletion job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
