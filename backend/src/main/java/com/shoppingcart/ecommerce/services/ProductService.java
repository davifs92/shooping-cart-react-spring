package com.shoppingcart.ecommerce.services;

import com.shoppingcart.ecommerce.dto.ProductDTO;
import com.shoppingcart.ecommerce.entities.Product;
import com.shoppingcart.ecommerce.exceptions.ResourceNotFoundException;
import com.shoppingcart.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> list = repository.findAll(pageRequest);
        return list.map
                (product -> new ProductDTO(product));

    }
    @Transactional (readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        return new ProductDTO(entity);
    }

    @Transactional (readOnly = true)
    public ProductDTO findStockProductByid(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setQtyInStock(entity.getQtyInStock());
        return dto;
    }
}
