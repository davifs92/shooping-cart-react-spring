package com.shoppingcart.ecommerce.controller;

import com.shoppingcart.ecommerce.dto.ProductDTO;
import com.shoppingcart.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService services;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                    @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
                                                    @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<ProductDTO> list = services.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = services.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/stock/{id}")
    public ResponseEntity<ProductDTO> findStockProductByid(@PathVariable Long id){
        ProductDTO dto = services.findById(id);
        return ResponseEntity.ok().body(dto);
    }

}
