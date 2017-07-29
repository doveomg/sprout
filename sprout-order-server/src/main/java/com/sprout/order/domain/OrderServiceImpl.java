package com.sprout.order.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderPersistence orderPersistence;

    @Transactional
    @Override
    public OrderNo newOrder(Integer goodsCount, Customer customer, GoodsInfo goodsInfo, Business business) {

        Orders orders = new Orders(goodsCount, customer, goodsInfo, business);
        orderPersistence.save(orders);

        //下单成功操作
        //TODO 发送短信通知商户有顾客下单
        //TODO 更新商品信息
        //TODO ...

        return orders.getOrderNo();
    }
}
