package org.hyunseokcheong.orderservice.service;

import org.hyunseokcheong.orderservice.dto.OrderDto;
import org.hyunseokcheong.orderservice.jpa.OrderEntity;

public interface OrderService {
	
	OrderDto createOrder(OrderDto orderDto);
	
	OrderDto getOrderByOrderId(String orderId);
	
	Iterable<OrderEntity> getOrdersByUserId(String userId);
}
