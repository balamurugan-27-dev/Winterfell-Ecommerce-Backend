package com.winterfell.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.winterfell.ecommerce.entity.Product;
import com.winterfell.ecommerce.repository.ProductRepository;
import com.winterfell.ecommerce.repository.UserRepository;

@Service
public class ProductService {

	ProductRepository productRepo;
	ImageService imageService;
	public ProductService(ProductRepository productRepo,ImageService imageService,UserRepository userRepo) {
		this.productRepo=productRepo;
		this.imageService=imageService;
		
	}
	
	public Product saveProduct(Product product,MultipartFile image) throws Exception {
		String imageurl=imageService.uploadImage(image,product);
		product.setImageUrl(imageurl);
		return productRepo.save(product);
	}

	public void deleteProduct(Long id) throws Exception {
		
		
		Product product=productRepo.findById(id).get();
		
		
		imageService.deleteImage(product.getPublicId());
		
		productRepo.deleteById(id);
		
	}

	public Page<Product> getProducts(int page) {
		
		return productRepo. findAll(PageRequest.of(page, 6	));
	}

	public Product getProduct(Long id) {
		
		return productRepo.findById(id).get();
	}
	
}
