package com.sprout.order.application.command;

import lombok.Data;
import lombok.ToString;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@Data
@ToString
public class OrdersCommand {

    private String goodsNo;

    private Integer goodsCount;

}
