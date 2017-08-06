package com.sprout.order.domain.enentCommons;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by fengshuaiju on 2017/8/6 0006.
 */
public interface SinkSender {

    String OUTPUT = "output";

    @Output("output")
    MessageChannel output();

}
