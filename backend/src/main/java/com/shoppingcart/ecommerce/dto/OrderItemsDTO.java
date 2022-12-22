package com.shoppingcart.ecommerce.dto;

import com.shoppingcart.ecommerce.entities.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemsDTO implements Serializable {

    private Long id;
    private Instant date;
    private int quantity;
    private Double price;
    private Long productId;
    private ProductDTO product;

    public OrderItemsDTO(OrderItems entity){
        this.id = entity.getId();
        this.date = entity.getDate();
        this.quantity = entity.getQuantity();
        this.price = entity.getPrice();
        this.productId = entity.getProduct().getId();
        this.product = new ProductDTO(entity.getProduct());
    }

}
