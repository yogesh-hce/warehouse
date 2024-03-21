package com.assignment.warehouse.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assignment.warehouse.entity.Product;
import com.assignment.warehouse.model.CreateProductRequest;
import com.assignment.warehouse.model.SearchProductResponse;
import com.assignment.warehouse.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody CreateProductRequest productRequest) {
    	if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        Product createdProduct = productService.createProduct(productRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdProduct.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}/box/{boxId}")
    public ResponseEntity<Object> updateBoxIdForProduct(@PathVariable Long id, @PathVariable Long boxId) {
        productService.updateProductBox(id, boxId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<SearchProductResponse> searchProducts(@PathVariable String searchTerm) {
        SearchProductResponse productResponse = productService.searchProducts(searchTerm);
        return ResponseEntity.ok(productResponse);
    }
}
