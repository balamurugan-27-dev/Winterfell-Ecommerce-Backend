package com.winterfell.ecommerce.service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Array;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.winterfell.ecommerce.entity.Cart;
import com.winterfell.ecommerce.entity.CartItem;
import com.winterfell.ecommerce.entity.Product;
import com.winterfell.ecommerce.entity.Users;
import com.winterfell.ecommerce.repository.CartItemRepository;
import com.winterfell.ecommerce.repository.CartRepository;
import com.winterfell.ecommerce.repository.ProductRepository;
import com.winterfell.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

	ProductRepository productRepo;
	CartRepository cartRepo;
	CartItemRepository cartItemRepo;
	UserRepository userRepo;
	
	public CartService(ProductRepository productRepo,CartRepository cartRepo,UserRepository userRepo,	CartItemRepository cartItemRepo) {
		this.cartRepo=cartRepo;
		this.productRepo=productRepo;
		this.userRepo=userRepo;
		this.cartItemRepo=cartItemRepo;
	}
	
	@Transactional
	public void addCart(long id,int quantity, Authentication authentication)  {
		
			String mail =authentication.getName();
		    Users user = userRepo.findByMail(mail)
		            .orElseThrow(() -> new RuntimeException("User not found"));			
			Cart cart=user.getCart();
			if(cart ==null) {
				cart=new Cart();
				cart.setUser(user);
			}
		    Product product = productRepo.findById(id)
		            .orElseThrow(() -> new RuntimeException("Product not found"));
		    
		    
		    for(CartItem citem:cart.getItems()) {
		    	if(citem.getProduct().getId()==id) {
		    		citem.setQuantity(citem.getQuantity()+quantity);
		    		return;
		    	}
		    }
		    
			CartItem item=new CartItem();
			item.setCart(cart);	                                                                                                                                                                                           
			item.setProduct(product);
			item.setQuantity(quantity);
			
			
			cart.getItems().add(item);
			
			
			cartRepo.save(cart);
	}
	
	public ResponseEntity<?> getcartitems(Authentication authentication){
		
		String mail=authentication.getName();
		
		Users user=userRepo.findByMail(mail).orElseThrow(()-> new UsernameNotFoundException("User not available"));
		

	    if (user.getCart() == null) {
	        return ResponseEntity.ok(new ArrayList<CartItem>());
	    }
		
		List<CartItem> cartItems=user.getCart().getItems();
		
		return  new ResponseEntity<>(cartItems,HttpStatusCode.valueOf(200));
		
	}
	
	public void deleteCartitem(long id,Authentication authentication) {
		String mail=authentication.getName();
		Users  user=userRepo.findByMail(mail).orElseThrow(()-> new UsernameNotFoundException("user name not available"));
		
		Cart cart=user.getCart();
		
		CartItem item=cartItemRepo.findById(id).orElseThrow(()-> new UsernameNotFoundException("product not found") );
		
		if(cart.getId() != item.getCart().getId())
			throw new RuntimeException("not allowed");
		
		cartItemRepo.deleteById(id);
	}

	public void updateQuantity(Long id, int quantity, Authentication authentication) {
		
		String mail=authentication.getName();
		
		Users  user=userRepo.findByMail(mail).orElseThrow(()-> new UsernameNotFoundException("user name not available"));
		
		Cart cart=user.getCart();
		
		CartItem item=cartItemRepo.findById(id).orElseThrow(()-> new UsernameNotFoundException("product not found") );
		
		
		if(cart.getId() != item.getCart().getId())
			throw new RuntimeException("not allowed");
		
		item.setQuantity(quantity);
		
		cartItemRepo.save(item);
		
		
	}
		
}
