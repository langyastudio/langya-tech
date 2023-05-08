package com.langyastudio.springboot.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author jiangjiaxiong
 * @date 2023年05月08日 08:00
 */
@Data
@ConfigurationProperties(prefix = "erp.db")
public class ErpDbConfig {
    private Boolean      compatibility;
    private String       schema;
    private List<String> replaceTables;
}
