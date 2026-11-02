package com.winterfell.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winterfell.ecommerce.entity.Product;
import com.winterfell.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	ProductService productService;
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@PostMapping("/saveProduct")
	public Product saveproduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}
}
