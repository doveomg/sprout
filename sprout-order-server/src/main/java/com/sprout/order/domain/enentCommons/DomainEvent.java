package com.sprout.order.domain.enentCommons;

import java.time.Instant;

/**
 * Created by fengshuaiju on 2017/8/6 0006.
 */
public interface DomainEvent {

    Instant occurredOn();

    int version();

}
