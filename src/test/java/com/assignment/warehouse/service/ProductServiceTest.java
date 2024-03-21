package com.assignment.warehouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.assignment.warehouse.entity.Product;
import com.assignment.warehouse.model.CreateProductRequest;
import com.assignment.warehouse.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct_Success() {
        Product product = new Product("Product Name");
        CreateProductRequest productRequest = new CreateProductRequest("Product Name");
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(productRequest);

        assertEquals(product.getName(), createdProduct.getName());
        Mockito.verify(productRepository).save(Mockito.any(Product.class));
    }

    @Test
    public void testGetProductById_Found() throws Exception {
        Long productId = 1L;
        Product expectedProduct = new Product("Product 1");
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        Product retrievedProduct = productService.getProductById(productId);

        assertEquals(expectedProduct, retrievedProduct);
        Mockito.verify(productRepository).findById(productId);
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        Long productId = 1L;
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
        Mockito.verify(productRepository).findById(productId);
    }
}