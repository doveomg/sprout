package com.sprout.order.domain;

import lombok.Data;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
//@Entity
@Data
public class GoodsInfo {

    private String goodsNo;

    private Double unitPrice;

    private String goodsName;

    private String goodsDesc;

    public GoodsInfo(String goodsNo, Double unitPrice, String goodsName, String goodsDesc) {
        this.goodsNo = goodsNo;
        this.unitPrice = unitPrice;
        this.goodsName = goodsName;
        this.goodsDesc = goodsDesc;
    }
}
