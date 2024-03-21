package com.assignment.warehouse.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.warehouse.entity.Box;
import com.assignment.warehouse.entity.Product;
import com.assignment.warehouse.model.CreateProductRequest;
import com.assignment.warehouse.model.SearchProductResponse;
import com.assignment.warehouse.repository.BoxRepository;
import com.assignment.warehouse.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    
    private final BoxRepository boxRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, BoxRepository boxRepository) {
        this.productRepository = productRepository;
        this.boxRepository = boxRepository;
    }

    public Product createProduct(CreateProductRequest productRequest) {
    	Product product = new Product(productRequest.getName());
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public SearchProductResponse searchProducts(String searchTerm) {
    	List<Product> results = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchTerm, searchTerm);
        results.sort(Comparator.comparing(Product::getName));
        SearchProductResponse products = new SearchProductResponse();
        results.forEach(result -> {
        	SearchProductResponse.ProductResponse productResponse = products.new ProductResponse();
        	productResponse.setName(result.getName());
        	productResponse.setDescription(result.getDescription());
			if (result.getBox() != null) {
				productResponse.setLocation(result.getBox().getLocation());
			}
			products.addProduct(productResponse);
        });
        return products;
    }
    
    public Product updateProductBox(Long productId, Long boxId) {
      Product product = productRepository.findById(productId).orElseThrow();
      Box box = boxRepository.findById(boxId).orElseThrow();
      product.setBox(box);
      productRepository.save(product);
      return product;
    }
}
