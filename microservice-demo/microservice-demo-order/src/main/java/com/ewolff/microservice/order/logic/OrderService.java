package com.ewolff.microservice.order.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewolff.microservice.order.clients.CatalogClient;
import com.ewolff.microservice.order.clients.CustomerClient;

@Service
class OrderService {

	private OrderRepository orderRepository;
	private CustomerClient customerClient;
	private CatalogClient itemClient;

	@Autowired
	private OrderService(OrderRepository orderRepository,
			CustomerClient customerClient, CatalogClient itemClient) {
		super();
		this.orderRepository = orderRepository;
		this.customerClient = customerClient;
		this.itemClient = itemClient;
	}

	public Order order(Order order) {
		if (order.getNumberOfLines() == 0) {
			throw new IllegalArgumentException("No order lines!");
		}
		if (!customerClient.isValidCustomerId(order.getCustomerId())) {
			throw new IllegalArgumentException("Customer does not exist!");
		}
		return orderRepository.save(order);
	}

	public double getPrice(long orderId) {
		return orderRepository.findOne(orderId).totalPrice(itemClient);
	}

}
