package com.winterfell.ecommerce.controller;


import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.winterfell.ecommerce.entity.Product;
import com.winterfell.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "https://winterfell-ecommerce-zaya-git-main-balamurugan2.vercel.app"
	})
public class ProductController {								
	
	ProductService productService;
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@GetMapping("/getproducts")
	public Page<Product> getProducts(@RequestParam("page")int page){	
		
		return productService.getProducts(page);
	}
	
	@GetMapping("/getproduct/{id}")
	public Product getProduct(@PathVariable Long id) {
		return productService.getProduct(id);
	}

    @PostMapping(
        value = "/saveproduct",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
	public Product saveproduct(@RequestPart("product") Product product,@RequestPart("image") MultipartFile image ) throws Exception {
		return productService.saveProduct(product,image);
	}
    
    @DeleteMapping("/deleteproduct")
    public void deleteProduct(@RequestParam("id") Long id) throws Exception {
    	 productService.deleteProduct(id);
    }
}
