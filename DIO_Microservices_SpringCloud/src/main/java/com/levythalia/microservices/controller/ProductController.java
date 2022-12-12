package com.levythalia.microservices.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levythalia.microservices.model.Product;
import com.levythalia.microservices.repository.ProductRepository;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
	
	private final ProductRepository productRepository;
	
	ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@PostMapping
	Product create(@RequestBody Product product) {
		return productRepository.save(product);
	}
}
