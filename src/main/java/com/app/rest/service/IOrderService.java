package com.app.rest.service;

import java.util.Optional;

import com.app.rest.model.Order;

public interface IOrderService {
	
	public Order save(Order order);
	
	public Optional<Order> getOrderByOrderId(Long id);
}
