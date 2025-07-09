package com.ecommerce.project.service;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);

    ProductResponse getProductByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws Exception;
    
}
