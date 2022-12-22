package com.shoppingcart.ecommerce.services;

import com.shoppingcart.ecommerce.dto.OrderDTO;
import com.shoppingcart.ecommerce.dto.OrderItemsDTO;
import com.shoppingcart.ecommerce.entities.Order;
import com.shoppingcart.ecommerce.entities.OrderItems;
import com.shoppingcart.ecommerce.repositories.OrderRepository;
import com.shoppingcart.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderDTO createNewOrder(OrderDTO dto) {
        Order order = new Order();
        copyDTOtoEntity(dto, order);
        order = orderRepository.save(order);
        return new OrderDTO(order, order.getOrderItems());

    }

    private void copyDTOtoEntity(OrderDTO dto, Order entity) {
        Optional.ofNullable(dto.getId()).ifPresent(entity::setId);
        entity.setSessionToken(UUID.randomUUID().toString());
        entity.setDate(Instant.now());
        entity.setItemsQuantity(dto.getItems().size());
        entity.setTotalAmount(calculateTotalAmount(dto.getItems()));
        copyOrderItemsDTOtoEntity(dto.getItems(), entity.getOrderItems());

    }

    private void copyOrderItemsDTOtoEntity(List<OrderItemsDTO> itemsDto, Set<OrderItems> entity){
        for(OrderItemsDTO item : itemsDto){
            OrderItems entityItems = new OrderItems();
            Optional.ofNullable(item.getId()).ifPresent(entityItems::setId);
            entityItems.setDate(Instant.now());
            entityItems.setPrice(item.getPrice());
            entityItems.setQuantity(item.getQuantity());
            entityItems.setProduct(productRepository.findById(item.getProductId()).get());
            entity.add(entityItems);
        }
    }

    public Double calculateTotalAmount(List<OrderItemsDTO> items) {
        double total = 0.0;
        for(OrderItemsDTO item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

}
