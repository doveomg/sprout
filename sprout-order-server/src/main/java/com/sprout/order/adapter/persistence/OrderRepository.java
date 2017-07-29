package com.sprout.order.adapter.persistence;

import com.sprout.order.domain.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@Repository
public class OrderRepository implements OrderPersistence {


    @Override
    public Page<Orders> findAll() {
        Integer goodsCount = 12;
        GoodsInfo goodsInfo = new GoodsInfo("CH1837", 12.8, "美味巧克力", "美味丝滑入口");
        Business business = new Business("美味零食店", "上海市南京东路168号", "13837904221", 4.8);
        Customer customer = new Customer("张晓晓","北京朝阳区","13619386420",109.5);

        List<Orders> orders = new ArrayList<>();
        orders.add(new Orders(goodsCount, customer, goodsInfo, business));
        orders.add(new Orders(goodsCount, customer, goodsInfo, business));
        orders.add(new Orders(goodsCount, customer, goodsInfo, business));
        orders.add(new Orders(goodsCount, customer, goodsInfo, business));
        orders.add(new Orders(goodsCount, customer, goodsInfo, business));

        Pageable pageable = new AbstractPageRequest(0, 20) {
            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previous() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }
        };
        PageImpl page = new PageImpl(orders,pageable,5);

        return page;
    }

    @Override
    public void save(Orders orders) {

    }
}
