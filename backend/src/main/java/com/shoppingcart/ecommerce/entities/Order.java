package com.shoppingcart.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;
    private int itemsQuantity;

    private String sessionToken;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant date;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER )
    private Set<OrderItems> orderItems = new HashSet<OrderItems>();

    public Set<OrderItems> getOrderItems() {
        return this.orderItems;
    }
}
