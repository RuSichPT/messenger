package com.github.rusichpt.Messenger1.services.impl;

import com.github.rusichpt.Messenger1.configs.JwtUtils;
import com.github.rusichpt.Messenger1.entities.BLackJwt;
import com.github.rusichpt.Messenger1.services.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class BLackListServiceImplTest {
    private final BLackListServiceImpl blackListService;
    private final JwtUtils jwtUtils;

    @MockBean // заменяем реальный сервис, чтобы не спамить почту на несуществующий адрес.
    private final EmailService emailService;

    @Autowired
    public BLackListServiceImplTest(BLackListServiceImpl blackListService, JwtUtils jwtUtils, EmailService emailService) {
        this.blackListService = blackListService;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
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