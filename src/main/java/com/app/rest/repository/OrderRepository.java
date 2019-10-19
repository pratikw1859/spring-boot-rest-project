package com.app.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.rest.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
