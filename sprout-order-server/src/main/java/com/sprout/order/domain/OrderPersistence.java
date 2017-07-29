package com.sprout.order.domain;

import org.springframework.data.domain.Page;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
public interface OrderPersistence {
    Page<Orders> findAll();

    void save(Orders orders);
}
