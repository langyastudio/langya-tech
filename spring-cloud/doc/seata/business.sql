-- seat-order：存储订单的数据库
create database `seat-order`;
use `seat-order`;

CREATE TABLE `seat-order`.`order_tbl`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `user_id`        varchar(255) DEFAULT NULL COMMENT '用户id',
    `commodity_code` varchar(255) DEFAULT NULL COMMENT '商品编码',
    `count`          int(11)      DEFAULT 0 COMMENT '数量',
    `money`          int(11)      DEFAULT 0 COMMENT '金额',
    `status`         int(1)       DEFAULT NULL COMMENT '订单状态：0：创建中；1：已完结',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Seata AT 模式需要使用日志回滚表 undo_log 表
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
CREATE TABLE `seat-order`.`undo_log`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20)   NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11)      NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    `ext`           varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


-- seat-storage：存储库存的数据库
create database `seat-storage`;
use `seat-storage`;

CREATE TABLE `seat-storage`.`storage_tbl`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `commodity_code` varchar(255) DEFAULT NULL COMMENT '商品编码',
    `count`          int(11)      DEFAULT 0 COMMENT '数量',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`commodity_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
INSERT INTO `seat-storage`.`storage_tbl` (`id`, `commodity_code`, `count`)
VALUES (1, 'C00321', 100);

-- Seata AT 模式需要使用日志回滚表 undo_log 表
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
CREATE TABLE `seat-storage`.`undo_log`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20)   NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11)      NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    `ext`           varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


-- seat-account：存储账户信息的数据库
create database `seat-account`;
use `seat-account`;

CREATE TABLE `seat-account`.`account_tbl`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
    `money`   int(11)      DEFAULT 0 COMMENT '金额',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
INSERT INTO `seat-account`.`account_tbl` (`id`, `user_id`, `money`)
VALUES (1, 'U100001', 1000);

-- Seata AT 模式需要使用日志回滚表 undo_log 表
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
CREATE TABLE `seat-account`.`undo_log`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20)   NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11)      NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    `ext`           varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;