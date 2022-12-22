package com.shoppingcart.ecommerce.controller;

import com.shoppingcart.ecommerce.dto.CartItemsDTO;
import com.shoppingcart.ecommerce.dto.ShoppingCartDTO;
import com.shoppingcart.ecommerce.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;


    @GetMapping(value = "/{id}")
    public ResponseEntity<ShoppingCartDTO> findById(@PathVariable Long id, @RequestHeader("sessionToken") String sessionToken) {
        ShoppingCartDTO shoppingCartDTO = service.findShoppingCart(id, sessionToken);
        return ResponseEntity.ok().body(shoppingCartDTO);
    }

    @PostMapping
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestBody ShoppingCartDTO dto, @RequestHeader("sessionToken") String sessionToken) {
        dto = service.createShoppingCart(dto, sessionToken);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ShoppingCartDTO> updateShoppingCart(@PathVariable Long id, @RequestHeader("sessionToken") String sessionToken, @RequestBody ShoppingCartDTO dto){
        dto = service.addToExistingShoppingCart(id, sessionToken, dto);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping( value = "/removeCartItem/{id}")
    public ResponseEntity<ShoppingCartDTO> deleteItem(@PathVariable("id") Long id, @RequestHeader("sessionToken") String sessionToken) {
        ShoppingCartDTO dto = service.removeItemFromShoppingCart(id,sessionToken);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping (value = "/updateQuantity/{id}")
    public ResponseEntity<CartItemsDTO> updateShoppingCartItem(@PathVariable Long id, @RequestBody CartItemsDTO dto){
        dto = service.increaseProductQuantity(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ShoppingCartDTO> deleteShoppingCart(@PathVariable Long id, @RequestHeader("sessionToken") String sessionToken){
        service.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

}
