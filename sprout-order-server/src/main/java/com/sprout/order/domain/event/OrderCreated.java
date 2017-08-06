package com.sprout.order.domain.event;

import com.sprout.order.domain.OrderNo;
import com.sprout.order.domain.enentCommons.AbstractDomainEvent;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengshuaiju on 2017/8/6 0006.
 */
public class OrderCreated extends AbstractDomainEvent{

    public OrderNo orderNo;

    public Map<String,Object> map = new HashMap<>();


    public OrderCreated(@NonNull OrderNo orderNo, @NonNull Map<String,Object> map){
        this.orderNo = orderNo;
        this.map = map;
    }


    public String toString() {
        return "com.sprout.order.domain.event.OrderCreated(orderNo=" + this.orderNo + ", map=" + this.map + ")";
    }
}
