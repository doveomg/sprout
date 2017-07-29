package com.sprout.order.domain;

import lombok.Data;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
//@Entity
@Data
public class Business {

    private String name;
    private String address;
    private String phone;
    private Double starLevel;


    public Business(String name, String address, String phone, Double starLevel) {
        this.name = name;
        this.address = address;
        this.setPhoneNumber(phone);
        this.giveStarLevel(starLevel);
    }


    private void giveStarLevel(Double starLevel){
        if(starLevel == null || starLevel < 0 || starLevel > 5){
            throw new IllegalArgumentException("place given correct level");
        }
        this.starLevel = starLevel;
    }

    private void setPhoneNumber(String phone){
        //TODO 加入手机号校验工具
        if(phone == null || phone.length() > 11){
            throw new IllegalArgumentException("place input correct phoneNumber");
        }
        this.phone = phone;
    }
}
