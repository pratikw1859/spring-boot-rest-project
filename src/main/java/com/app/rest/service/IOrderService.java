package com.app.rest.service;

import java.util.List;
import java.util.Optional;

import com.app.rest.model.Order;

public interface IOrderService {
	
	public Order save(Order order);
	
	public Optional<Order> getOrderByOrderId(Long id);
	
	public List<Order> saveAll(List<Order> orders);
}
