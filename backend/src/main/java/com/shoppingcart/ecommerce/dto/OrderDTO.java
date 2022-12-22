package com.shoppingcart.ecommerce.dto;

import com.shoppingcart.ecommerce.entities.Order;
import com.shoppingcart.ecommerce.entities.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO implements Serializable {
    private Long id;
    private Instant date;
    private Double totalAmount;
    private int itemsQuantity;
    private List<OrderItemsDTO> items = new ArrayList();

    public OrderDTO(Order entity){
        this.id = entity.getId();
        this.date = entity.getDate();
        this.totalAmount = entity.getTotalAmount();
        this.itemsQuantity = entity.getItemsQuantity();
    }

    public OrderDTO(Order entity, Set<OrderItems> orderItems){
        this(entity);
        orderItems.forEach(item -> this.items.add(new OrderItemsDTO(item)));
    }
}
