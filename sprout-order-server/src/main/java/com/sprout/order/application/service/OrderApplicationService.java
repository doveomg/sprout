package com.sprout.order.application.service;

import com.sprout.order.application.command.OrdersCommand;
import com.sprout.order.application.representation.OrderRepresentation;
import com.sprout.order.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@Service
@Slf4j
public class OrderApplicationService {

    @Autowired
    private OrderPersistence orderPersistence;

    @Autowired
    private OrderService orderService;


    public Page<OrderRepresentation> getOrders() {

        Page<Orders> orders = orderPersistence.findAll();

        log.debug("find all orders ");

        return orders.map(o -> OrderRepresentation.converter(o));

    }

    public OrderNo createOrder(OrdersCommand ordersCommand) {
        String goodsNo = ordersCommand.getGoodsNo();
        Integer goodsCount = ordersCommand.getGoodsCount();

        if(goodsCount == null || goodsCount <=0 ){
            throw new IllegalArgumentException("place choice the goods count");
        }

        //TODO 根据商品编号，查找商品详情
        GoodsInfo goodsInfo = new GoodsInfo("CH1837", 12.8, "美味巧克力", "美味丝滑入口");

        //TODO 根据商品获取商家信息
        Business business = new Business("美味零食店", "上海市南京东路168号", "13837904221", 4.8);

        //TODO 获取当前下单用户
        Customer customer = new Customer("张晓晓","北京朝阳区","13619386420",109.5);

        //TODO 检查库存

        return orderService.newOrder(goodsCount, customer, goodsInfo, business);

    }



}
