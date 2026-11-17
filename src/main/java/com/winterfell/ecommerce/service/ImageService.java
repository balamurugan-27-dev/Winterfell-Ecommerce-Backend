package com.winterfell.ecommerce.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.winterfell.ecommerce.entity.Product;

@Service
public class ImageService {
	
	Cloudinary cloudinary;
	
	public ImageService(Cloudinary cloudinary) {
		
		this.cloudinary=cloudinary;
	}
	
	@SuppressWarnings("unchecked")
	public String  uploadImage(MultipartFile image,Product product) throws IOException {
		
		String publicId=UUID.randomUUID().toString();

		

		Map<String,Object> result=cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap(
				"folder","ecommerce/products",
				"public_id",publicId)
				);
		product.setPublicId(result.get("public_id").toString());
		return result.get("secure_url").toString();
	
		
	}

	public void deleteImage(String publicId) throws Exception {
		
		cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
		
	}
	
}
