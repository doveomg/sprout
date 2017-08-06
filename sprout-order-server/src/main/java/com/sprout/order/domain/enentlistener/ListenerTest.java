package com.sprout.order.domain.enentlistener;

import com.alibaba.fastjson.JSONObject;
import com.sprout.order.domain.enentCommons.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by fengshuaiju on 2017/8/6 0006.
 */
@Component
@Slf4j
public class ListenerTest {

    @EventListener
    public void test(DomainEvent event){

        System.out.println("----------------------->"+event.toString());

        //String s = JSONObject.toJSONString(object);
        //log.debug("----------------------->" + s);
    }

}