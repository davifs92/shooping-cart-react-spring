package com.shoppingcart.ecommerce.tests;

import com.shoppingcart.ecommerce.dto.OrderDTO;
import com.shoppingcart.ecommerce.dto.ProductDTO;
import com.shoppingcart.ecommerce.entities.Order;
import com.shoppingcart.ecommerce.entities.OrderItems;
import com.shoppingcart.ecommerce.entities.Product;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Factory {
	
	public static Product createProduct() {
		return new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", 10);
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}

	public static Order createOrder(){
		return new Order(1L, 500.0, 5, UUID.randomUUID().toString(), Instant.now(), createOrderItem() );
	}

	public static OrderDTO createOrderDTO() {
		Order order = createOrder();
		return new OrderDTO(order);

	}

	public static Set<OrderItems> createOrderItem() {
		OrderItems orderItems = new OrderItems(1L, 3, 100.0, Instant.now(), createProduct());
		Set<OrderItems> set = new HashSet<OrderItems>();
		set.add(orderItems);
		return set;
	}
}
