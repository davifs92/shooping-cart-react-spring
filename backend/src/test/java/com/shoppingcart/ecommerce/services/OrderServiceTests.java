package com.shoppingcart.ecommerce.services;

import com.shoppingcart.ecommerce.dto.OrderDTO;
import com.shoppingcart.ecommerce.entities.Order;
import com.shoppingcart.ecommerce.repositories.OrderRepository;
import com.shoppingcart.ecommerce.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() throws Exception {
        Order order = Factory.createOrder();

        Mockito.when(orderRepository.save(any())).thenReturn(order);
    }

    @Test
    public void shouldReturnTheOrderAfterSaved() {

        OrderDTO orderDTOToBeSaved = Factory.createOrderDTO();

        OrderDTO orderDTOSaved = orderService.createNewOrder(orderDTOToBeSaved);

        Assertions.assertNotNull(orderDTOSaved);
        Assertions.assertNotNull(orderDTOSaved.getId());
    }



}
