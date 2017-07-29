package com.sprout.order.domain;

import lombok.Data;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@Data
public class Customer {

    private String name;
    private String address;
    private String phone;
    private Double balance;


    public Customer(String name, String address, String phone, Double balance) {
        this.name = name;
        this.address = address;
        this.setPhoneNumber(phone);
        this.setBalance(balance);
    }


    private void setPhoneNumber(String phone){
        //TODO 加入手机号校验工具
        if(phone == null || phone.length() > 11){
            throw new IllegalArgumentException("place input correct phoneNumber");
        }
        this.phone = phone;
    }


    private void setBalance(Double balance){
        if(balance == null || balance < 0){
            throw new IllegalArgumentException("place input correct balance");
        }
        this.balance = balance;
    }
}
