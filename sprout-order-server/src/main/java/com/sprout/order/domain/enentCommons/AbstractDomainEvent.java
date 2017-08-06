package com.sprout.order.domain.enentCommons;

import java.time.Instant;

/**
 * Created by fengshuaiju on 2017/8/6 0006.
 */
public class AbstractDomainEvent implements DomainEvent {

    private static final int DEFAULT_VERSION = 1;

    private Instant occurredOn;
    private int version;

    public AbstractDomainEvent(){
        new AbstractDomainEvent(DEFAULT_VERSION);
    }

    public AbstractDomainEvent(int version){
        new AbstractDomainEvent(Instant.now(), version);
    }


    public AbstractDomainEvent(Instant occurredOn, int version){
        this.occurredOn = occurredOn;
        this.version = version;
    }


    @Override
    public Instant occurredOn() {
        return this.occurredOn;
    }

    @Override
    public int version() {
        return this.version;
    }
}
