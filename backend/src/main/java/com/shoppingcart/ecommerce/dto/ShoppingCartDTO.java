package com.shoppingcart.ecommerce.dto;

import com.shoppingcart.ecommerce.entities.CartItems;
import com.shoppingcart.ecommerce.entities.ShoppingCart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class ShoppingCartDTO implements Serializable {
    private Long id;
    private Instant date;
    @Size(min = 1, message = "It needs at least one item")
    @NotBlank(message = "Cart Items cannot be blank")
    private List<CartItemsDTO> items = new ArrayList();
    private String sessionToken;

    public ShoppingCartDTO(ShoppingCart entity){
        this.id = entity.getId();
        this.date = entity.getDate();
        this.sessionToken = entity.getSessionToken();
    }

    public ShoppingCartDTO(ShoppingCart entity, Set<CartItems> cartItems){
        this(entity);
        cartItems.forEach(item -> this.items.add(new CartItemsDTO(item)));
    }

}
