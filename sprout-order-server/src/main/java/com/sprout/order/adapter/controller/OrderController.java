package com.sprout.order.adapter.controller;

import com.sprout.order.application.command.OrdersCommand;
import com.sprout.order.application.representation.OrderRepresentation;
import com.sprout.order.application.service.OrderApplicationService;
import com.sprout.order.domain.OrderNo;
import com.sprout.order.domain.enentCommons.DomainEventPublish;
import com.sprout.order.domain.event.OrderCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@RestController
@Slf4j
public class OrderController {

	@Autowired
	private OrderApplicationService orderApplicationService;

	@GetMapping("/orders")
	@ResponseStatus(HttpStatus.OK)
	public Page<OrderRepresentation> getOrders(){
		log.debug("get orders {}", "AAAA");

		Map<String,Object> map = new HashMap<>();
		map.put("str1","name1");
		map.put("str2","name2");

		DomainEventPublish.publish(new OrderCreated(OrderNo.generate(),map));

		return orderApplicationService.getOrders();
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderNo createOrder(@RequestBody OrdersCommand ordersCommand){

		log.info("create order , order info {}", ordersCommand.toString());

		return orderApplicationService.createOrder(ordersCommand);

	}
	

}
