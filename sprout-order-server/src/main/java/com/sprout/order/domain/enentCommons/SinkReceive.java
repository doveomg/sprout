package com.sprout.order.domain.enentCommons;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by fengshuaiju on 2017/8/6 0006.
 */

public interface SinkReceive {

    String INPUT = "input";

    @Input("input")
    SubscribableChannel input();

}
