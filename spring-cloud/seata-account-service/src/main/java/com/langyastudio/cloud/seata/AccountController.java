package com.langyastudio.cloud.seata;

import java.security.SecureRandom;
import io.seata.core.context.RootContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AccountController
{
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    private final JdbcTemplate jdbcTemplate;
    private final SecureRandom random;

    public AccountController(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.random = new SecureRandom();
    }

    /**
     * 更新用户的 money
     *
     * @param userId 用户Id号
     * @param money  减少的金额
     * @return
     */
    @GetMapping(value = "/account", produces = "application/json")
    public String account(String userId, int money)
    {
        log.info("Account Service ... xid: " + RootContext.getXID());

        if (random.nextBoolean())
        {
            throw new RuntimeException("this is a mock Exception");
        }

        int result = jdbcTemplate.update(
                "update account_tbl set money = money - ? where user_id = ?",
                new Object[]{money, userId});

        log.info("Account Service End ... ");
        if (result == 1)
        {
            return SUCCESS;
        }

        return FAIL;
    }

}
