package com.langyastudio.cloud.seata;

import io.seata.core.context.RootContext;
import lombok.extern.log4j.Log4j2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
public class StorageController
{
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    private final JdbcTemplate jdbcTemplate;

    public StorageController(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping(value = "/storage/{commodityCode}/{count}", produces = "application/json")
    public String echo(@PathVariable String commodityCode, @PathVariable int count)
    {
        log.info("Storage Service Begin ... xid: " + RootContext.getXID());

		/**
		 * 减少库存数据
		 */
		int result = jdbcTemplate.update(
                "update storage_tbl set count = count - ? where commodity_code = ?",
                new Object[]{count, commodityCode});

		log.info("Storage Service End ... ");
        if (result == 1)
        {
            return SUCCESS;
        }

        return FAIL;
    }
}
