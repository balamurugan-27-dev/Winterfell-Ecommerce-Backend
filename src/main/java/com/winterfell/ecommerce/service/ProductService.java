package com.winterfell.ecommerce.service;

import org.springframework.stereotype.Service;

import com.winterfell.ecommerce.entity.Product;
import com.winterfell.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	ProductRepository productRepo;
	
	public ProductService(ProductRepository productRepo) {
		this.productRepo=productRepo;
	}
	
	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}
	
}
