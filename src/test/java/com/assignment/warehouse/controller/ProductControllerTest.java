package com.assignment.warehouse.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.assignment.warehouse.entity.Product;
import com.assignment.warehouse.model.CreateProductRequest;
import com.assignment.warehouse.model.SearchProductResponse;
import com.assignment.warehouse.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;
    
    @Mock
    private ServletRequestAttributes requestAttributes;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(requestAttributes);
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product("Product 1");
        CreateProductRequest createProductRequest = new CreateProductRequest("Product 1");
        Mockito.when(productService.createProduct(createProductRequest)).thenReturn(product);
        
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(requestAttributes.getRequest()).thenReturn(mockRequest);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        ResponseEntity<Object> response = productController.createProduct(createProductRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(productService).createProduct(createProductRequest);
    }

    @Test
    public void testSearchProducts() throws Exception {
        String searchTerm = "laptop";
        SearchProductResponse searchProductResponse = new SearchProductResponse();
        Mockito.when(productService.searchProducts(searchTerm)).thenReturn(searchProductResponse);

        ResponseEntity<SearchProductResponse> response = productController.searchProducts(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(searchProductResponse, response.getBody());
        Mockito.verify(productService).searchProducts(searchTerm);
    }
}