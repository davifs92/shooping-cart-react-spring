package com.shoppingcart.ecommerce.dto;

import com.shoppingcart.ecommerce.entities.CartItems;
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
public class CartItemsDTO implements Serializable {
    private Long id;
    private Instant date;
    private int quantity;
    private Double price;
    private ProductDTO product;

    public CartItemsDTO(CartItems entity){
        this.id = entity.getId();
        this.date = entity.getDate();
        this.quantity = entity.getQuantity();
        this.price = entity.getPrice();
        this.product = new ProductDTO(entity.getProduct());
    }

}
