package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/api/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId) {
        ProductDTO savedproductDTO = productService.addProduct(productDTO, categoryId);
        return new ResponseEntity<>(savedproductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/api/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/api/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/api/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productService.getProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/api/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId) {
        ProductDTO updatedProductDTO = productService.updateProduct(productDTO, productId);                                                

        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);                                            
    }

    @DeleteMapping("/api/product/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO deletedProductDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProductDTO, HttpStatus.OK);
    }

    @PutMapping("/api/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam(name = "image") MultipartFile image) throws Exception {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
