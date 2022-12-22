package com.shoppingcart.ecommerce.services;

import com.shoppingcart.ecommerce.dto.CartItemsDTO;
import com.shoppingcart.ecommerce.dto.ShoppingCartDTO;
import com.shoppingcart.ecommerce.entities.CartItems;
import com.shoppingcart.ecommerce.entities.ShoppingCart;
import com.shoppingcart.ecommerce.exceptions.DataBaseException;
import com.shoppingcart.ecommerce.exceptions.ResourceNotFoundException;
import com.shoppingcart.ecommerce.repositories.ListItemsRepository;
import com.shoppingcart.ecommerce.repositories.ProductRepository;
import com.shoppingcart.ecommerce.repositories.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ListItemsRepository listItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ShoppingCartDTO findShoppingCart(Long id, String sessionToken){
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findBySessionTokenAndId(sessionToken, id);
        ShoppingCart entity = shoppingCart.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        return new ShoppingCartDTO(entity, entity.getItems());

    }

    @Transactional
    public ShoppingCartDTO createShoppingCart(ShoppingCartDTO dto, String sessionToken) {
        ShoppingCart shoppingCart = new ShoppingCart();
        dto.setSessionToken(sessionToken);
        copyDTOtoEntity(dto, shoppingCart);
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        return new ShoppingCartDTO(shoppingCart, shoppingCart.getItems());

    }

    @Transactional
    public ShoppingCartDTO addToExistingShoppingCart(Long id, String sessionToken, ShoppingCartDTO dto) {
            ShoppingCart shoppingCart = shoppingCartRepository.findBySessionTokenAndId(sessionToken, id).
                    orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
            copyDTOtoEntity(dto, shoppingCart);
            shoppingCart = shoppingCartRepository.saveAndFlush(shoppingCart);
            return new ShoppingCartDTO(shoppingCart, shoppingCart.getItems());
    }

    @Transactional
    public CartItemsDTO increaseProductQuantity(Long id, CartItemsDTO dto) {
        try {
            CartItems cartItems = listItemsRepository.getReferenceById(id);
            cartItems.setQuantity(dto.getQuantity());
            return new CartItemsDTO(listItemsRepository.save(cartItems));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Item not found");
        }
    }

    @Transactional
    public ShoppingCartDTO removeItemFromShoppingCart(Long id, String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken).
                orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        Set<CartItems> items = shoppingCart.getItems();
        CartItems itemToBeRemoved = new CartItems();
        for(CartItems item : items){
            if (item.getId().equals(id)){
                itemToBeRemoved = item;
            }
        }
        items.remove(itemToBeRemoved);
        listItemsRepository.delete(itemToBeRemoved);
        return new ShoppingCartDTO(shoppingCartRepository.save(shoppingCart));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCart(Long id){
        try {
            shoppingCartRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found");
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDTOtoEntity(ShoppingCartDTO dto, ShoppingCart entity) {
        Optional.ofNullable(dto.getId()).ifPresent(entity::setId);
        entity.setSessionToken(dto.getSessionToken());
        entity.setDate(dto.getDate());
        copyListItemsDTOtoEntity(dto.getItems(), entity.getItems());

    }

    private void copyListItemsDTOtoEntity(List<CartItemsDTO> itemsDto, Set<CartItems> entity){
        for(CartItemsDTO item : itemsDto){
            CartItems entityItems = new CartItems();
            Optional.ofNullable(item.getId()).ifPresent(entityItems::setId);
            entityItems.setDate(Instant.now());
            entityItems.setPrice(item.getPrice());
            entityItems.setQuantity(item.getQuantity());
            entityItems.setProduct(productRepository.findById(item.getProduct().getId()).get());
            entity.add(entityItems);
        }
    }


}
