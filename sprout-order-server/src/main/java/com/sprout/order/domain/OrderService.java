package com.sprout.order.domain;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
public interface OrderService {
    OrderNo newOrder(Integer goodsCount, Customer customer, GoodsInfo goodsInfo, Business business);
}
