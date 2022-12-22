package com.shoppingcart.ecommerce.services;

import com.shoppingcart.ecommerce.dto.ProductDTO;
import com.shoppingcart.ecommerce.entities.Product;
import com.shoppingcart.ecommerce.repositories.ProductRepository;
import com.shoppingcart.ecommerce.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		long existingId = 1L;
		long nonExistingId = 2L;
		long dependentId = 3L;
		Product product = Factory.createProduct();
		PageImpl<Product> page = new PageImpl<>(List.of(product));
		
		Mockito.when(repository.findAll((Pageable)any())).thenReturn(page);
		
		Mockito.when(repository.save(any())).thenReturn(product);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		

		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Page<ProductDTO> result = service.findAllPaged(PageRequest.of(0, 12));
		
		Assertions.assertNotNull(result);
	}


	@Test
	public void findByIdShouldReturnProduct(){
		ProductDTO result = service.findById(1L);

		Assertions.assertEquals(result.getId(), 1L);
	}

}
