package com.sprout.order.domain;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@Entity
public class Orders {
    //extends AbstractEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    private OrderNo orderNo;

    private Double totalPrice;

    @Column(length = 32)
    private String goodsNo;

    private Integer goodsCount;

    @Column(length = 128, nullable = false)
    private String goodsName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(length = 16, nullable = false)
    private String shipper;

    @Column(length = 255)
    private String goodsDesc;

    private Instant signInTime = Instant.now();

    @Column(length = 16, nullable = false)
    private String receiver;

    @Column(length = 128, nullable = false)
    private String receiverAddress;

    @Column(length = 11, nullable = false)
    private String receiverPhone;

    private Boolean isPaid;

    public OrderNo getOrderNo() {
        return this.orderNo;
    }

    enum OrderStatus{
        PENDING,
        PROCESSING,
        DELIVERY,
        FINISHED,
        CANCELED
    }


    public String getGoodsName() {
        return goodsName;
    }

    public String getOrderStatus() {
        return orderStatus.name();
    }

    public String getShipper() {
        return shipper;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public String getSignInTime() {
        return LocalDateTime.ofInstant(signInTime, ZoneId.systemDefault()).toString();
    }

    public String getReceiver() {
        return receiver;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    private Orders(){

    }


    public Orders(Integer goodsCount, Customer customer, GoodsInfo goodsInfo, Business business){

        this.orderNo = OrderNo.generate();
        this.isPaid = false;
        this.orderStatus = OrderStatus.PENDING;

        this.totalPrice = goodsCount * goodsInfo.getUnitPrice();

        this.goodsNo = goodsInfo.getGoodsNo();
        this.goodsCount = goodsCount;
        this.goodsDesc = goodsInfo.getGoodsDesc();

        this.goodsName = goodsInfo.getGoodsName();

        this.shipper = business.getName();

        this.receiver = customer.getName();
        this.receiverAddress = customer.getAddress();
        this.receiverPhone = customer.getPhone();

    }

}
