package com.sprout.order.application.representation;

import com.sprout.order.domain.Orders;
import lombok.Data;
import lombok.ToString;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@Data
@ToString
public class OrderRepresentation {

    private String goodsName;

    private String goodsDesc;

    private String orderStatus;

    private String shipper;

    private String signInTime;

    private String receiver;

    private String receiverAddress;

    private String receiverPhone;


    public static OrderRepresentation converter(Orders orders){

        OrderRepresentation orderRepresentation = new OrderRepresentation();

        orderRepresentation.goodsName = orders.getGoodsName();
        orderRepresentation.goodsDesc = orders.getGoodsDesc();
        orderRepresentation.orderStatus = orders.getOrderStatus();
        orderRepresentation.shipper = orders.getShipper();
        orderRepresentation.signInTime = orders.getSignInTime();
        orderRepresentation.receiver = orders.getReceiver();
        orderRepresentation.receiverAddress = orders.getReceiverAddress();
        orderRepresentation.receiverPhone = orders.getReceiverPhone();

        return orderRepresentation;
    }

}
