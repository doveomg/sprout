package com.sprout.order.domain.enentCommons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by fengshuaiju on 2017/8/6 0006.
 */
@Component
public class DomainEventPublish implements ApplicationContextAware{

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static void publish(AbstractDomainEvent abstractDomainEvent){
        context.publishEvent(abstractDomainEvent);
    }

}
