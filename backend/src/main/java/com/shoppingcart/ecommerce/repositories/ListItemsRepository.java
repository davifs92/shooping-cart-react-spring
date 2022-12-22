package com.shoppingcart.ecommerce.repositories;

import com.shoppingcart.ecommerce.entities.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListItemsRepository extends JpaRepository<CartItems, Long> {
}
