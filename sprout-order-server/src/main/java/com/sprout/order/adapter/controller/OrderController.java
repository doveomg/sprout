package com.sprout.order.adapter.controller;

import com.sprout.order.application.command.OrdersCommand;
import com.sprout.order.application.representation.OrderRepresentation;
import com.sprout.order.application.service.OrderApplicationService;
import com.sprout.order.domain.OrderNo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
		return orderApplicationService.getOrders();
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderNo createOrder(@RequestBody OrdersCommand ordersCommand){

		log.info("create order , order info {}", ordersCommand.toString());

		return orderApplicationService.createOrder(ordersCommand);

	}
	

}
