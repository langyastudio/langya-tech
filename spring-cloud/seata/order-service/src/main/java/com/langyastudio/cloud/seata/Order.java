package com.langyastudio.cloud.seata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Order implements Serializable
{
    /**
     * id.
     */
    private long id;

    /**
     * user id.
     */
	private String userId;

    /**
     * commodity code.
     */
	private String commodityCode;

    /**
     * count.
     */
	private int count;

    /**
     * money.
     */
	private int money;
}
