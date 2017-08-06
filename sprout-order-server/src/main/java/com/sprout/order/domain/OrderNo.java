package com.sprout.order.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@AttributeOverride(name = "orderNo", column = @Column(name = "orderNo"))
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class OrderNo {

    private String orderNo;

    public static OrderNo generate(){
        OrderNo orderNo = new OrderNo();
        orderNo.orderNo = UUID.randomUUID().toString().replace("-", "");
        return orderNo;
    }

    public String getOrderNo(){
        return orderNo;
    }
}
