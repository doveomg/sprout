package com.sprout.order.domain.enentlistener;

import com.alibaba.fastjson.JSONObject;
import com.sprout.order.domain.enentCommons.SinkReceive;
import com.sprout.order.domain.enentCommons.SinkSender;
import lombok.extern.log4j.Log4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
* Created by fengshuaiju on 2017/8/6 0006.
*/

@EnableBinding(value = {SinkReceive.class, SinkSender.class})
@Log4j
public class EnentListener {

    @StreamListener(SinkReceive.INPUT)
    public void receive(Object object) {
        String s1 = JSONObject.toJSONString(object);
        log.error(s1);
    }

}