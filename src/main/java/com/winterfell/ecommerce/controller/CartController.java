package com.winterfell.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winterfell.ecommerce.entity.CartItem;
import com.winterfell.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("https://winterfell-ecommerce-zaya.vercel.app")
public class CartController {

	CartService cartService;

	public CartController(CartService cartService) {
	
		this.cartService=cartService;
		
	}
	

	
	
	
	@PostMapping("/addcart/{id}/{quantity}")
	public ResponseEntity<String> addCart(@PathVariable("id") long id,
	        @PathVariable("quantity") int quantity,Authentication authentication) {
		
		try {
		cartService.addCart( id,quantity,authentication);
		return new ResponseEntity<String>("success",HttpStatus.OK);
		}
		catch(Exception e) {
	
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
			
	}
		
	@GetMapping("/getcartitems")
	public  ResponseEntity<List<CartItem>> getCartItems(Authentication authentication){
		
		return  (ResponseEntity<List<CartItem>>) cartService.getcartitems(authentication);
	}
	
	@DeleteMapping("/deletecartitem")
	public void deleteCartItem(@RequestParam Long id,Authentication authentication) {
		
		cartService.deleteCartitem(id, authentication);	
		
	}
	
	@PutMapping("updatequantity")
	public void updateQuantity(@RequestParam Long id,@RequestParam int quantity ,Authentication authentication)	 {
		
		cartService.updateQuantity(id,quantity,authentication);
	}
	
	@GetMapping("/test")
	public String test(Authentication authentication) {
	    return authentication.getName();
	}
}
