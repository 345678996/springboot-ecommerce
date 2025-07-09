package com.ecommerce.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> 
                        new ResourceNotFoundException("Category", "categoryId", categoryId));
        Product product = modelMapper.map(productDTO, Product.class); 

        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);
        
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()) {
            throw new APIException("No product saved till now");
        }
        List<ProductDTO> productDTOs = products.stream()
                            .map(product -> modelMapper.map(product, ProductDTO.class))
                            .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> 
                            new ResourceNotFoundException("Category", "category_id", categoryId));
        
        // List<Product> products = category.getProducts();  <-- Fetched product from category using bidirectional mapping
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        if(products.isEmpty()) {
            throw new APIException("No product saved till now");
        }
        List<ProductDTO> productDTOs = products.stream()
                                .map(product -> modelMapper.map(product, ProductDTO.class))
                                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);

        return productResponse;
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        if(products.isEmpty()) {
            throw new APIException("No product saved till now");
        }
        List<ProductDTO> productDTOs = products
                                .stream()
                                .map(product -> modelMapper.map(product, ProductDTO.class))
                                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
       Product existingProduct = productRepository.findById(productId)
                        .orElseThrow(() -> 
                        new ResourceNotFoundException("Product", "productId", productId));
        Product product = modelMapper.map(productDTO, Product.class);

        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSpecialPrice(product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice()));

        Product savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(existingProduct);
        return modelMapper.map(existingProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws Exception {
        // Get product from DB
        Product productFromDB = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        // Upload image to server (file system)
        // And get the file name
        String path = "images/";
        String fileName = uploadImage(path, image);
        
        //Updating the new file name in DB
        productFromDB.setImage(fileName);
        Product updatdeProduct = productRepository.save(productFromDB);
        return modelMapper.map(updatdeProduct, ProductDTO.class);
    }

    private String uploadImage(String path, MultipartFile file) throws Exception {
        // Get original file name
        String originalFileName = file.getOriginalFilename();
        //Generate a random file name
        String randomId = UUID.randomUUID().toString();
        // mat.jps --> 1234 --> 1234.jpg
        String newFileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + newFileName;
        // Check if path already exist otherwise create
        File folder = new File(path);
        if(!folder.exists()) folder.mkdir();

        // Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newFileName;
    }

}
