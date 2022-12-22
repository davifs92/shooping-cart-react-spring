package com.shoppingcart.ecommerce.services;

import com.shoppingcart.ecommerce.dto.ProductDTO;
import com.shoppingcart.ecommerce.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository repository;

	private Long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		Long existingId = 1L;
		Long nonExistingId = 1000L;
		countTotalProducts = 9L;
	}
	
	@Test
	public void findAllPagedShouldReturnPageWhenPage0Size10() {
		
		Page<ProductDTO> result = service.findAllPaged(PageRequest.of(0, 10));
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
		

		Page<ProductDTO> result = service.findAllPaged(PageRequest.of(50, 10));
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		
		Page<ProductDTO> result = service.findAllPaged(PageRequest.of(0, 10, Sort.by("name")));
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());		
	}
}
