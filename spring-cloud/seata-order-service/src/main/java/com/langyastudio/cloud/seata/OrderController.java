package com.langyastudio.cloud.seata;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import io.seata.core.context.RootContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class OrderController
{
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL    = "FAIL";
    private static final String USER_ID = "U100001";

    private final JdbcTemplate jdbcTemplate;
    private final SecureRandom random;

    private final SeataOrderApplication.AccountService accountService;

    public OrderController(JdbcTemplate jdbcTemplate, SeataOrderApplication.AccountService accountService)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.accountService = accountService;
        this.random = new SecureRandom();
    }

    @PostMapping(value = "/order", produces = "application/json")
    public String order(String userId, String commodityCode, int orderCount)
    {
        log.info("Order Service Begin ... xid: " + RootContext.getXID());

		//商品算2元
        int orderMoney = 2 * orderCount;

		//扣除个人账户
        accountService.account(USER_ID, orderMoney);

		//生成订单
        final Order order = new Order(0, userId, commodityCode, orderCount, orderMoney);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int result = jdbcTemplate.update(con -> {
			PreparedStatement pst = con.prepareStatement(
					"insert into order_tbl (user_id, commodity_code, count, money) values (?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setObject(1, order.getUserId());
			pst.setObject(2, order.getCommodityCode());
			pst.setObject(3, order.getCount());
			pst.setObject(4, order.getMoney());
			return pst;
		}, keyHolder);

        order.setId(keyHolder.getKey().longValue());

		//抛出异常，用于测试整个事务流程
        if (random.nextBoolean())
        {
            //throw new RuntimeException("this is a mock Exception");
        }

        log.info("Order Service End ... Created " + order);

        if (result == 1)
        {
            return SUCCESS;
        }
        return FAIL;
    }
}
