package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.configs.JwtUtils;
import com.github.rusichpt.Messenger.entities.BLackJwt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class BLackListServiceImplTest {
    private final BLackListServiceImpl blackListService;
    private final JwtUtils jwtUtils;

    @Autowired
    public BLackListServiceImplTest(BLackListServiceImpl blackListService, JwtUtils jwtUtils) {
        this.blackListService = blackListService;
        this.jwtUtils = jwtUtils;
    }

    @Test
    void findAllJwtByExpirationBefore() throws InterruptedException {
        BLackJwt bLackJwt1 = new BLackJwt(jwtUtils.generateToken("12"), LocalDateTime.now());
        Thread.sleep(2);
        BLackJwt bLackJwt2 = new BLackJwt(jwtUtils.generateToken("21"), LocalDateTime.now());
        Thread.sleep(2);
        blackListService.saveJwt(bLackJwt1);
        blackListService.saveJwt(bLackJwt2);

        blackListService.deleteAllJwtByExpirationBefore(LocalDateTime.now());

        List<BLackJwt> allJwt = blackListService.findAllJwt();
        Assertions.assertEquals(allJwt.size(), 0);
    }

}