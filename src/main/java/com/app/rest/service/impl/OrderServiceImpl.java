package com.app.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.rest.model.Order;
import com.app.rest.repository.OrderRepository;
import com.app.rest.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
	
	private OrderRepository orderRepo;

	public OrderServiceImpl(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}
	
	@Override
	public Order save(Order order) {
		return orderRepo.save(order);
	}
	
	@Override
	public Optional<Order> getOrderByOrderId(Long id) {
		return orderRepo.findById(id);
	}
	
	@Override
	public List<Order> saveAll(List<Order> orders) {
		return orderRepo.saveAll(orders);
	}
}
