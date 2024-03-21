package com.assignment.warehouse.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

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

import com.assignment.warehouse.entity.Warehouse;
import com.assignment.warehouse.model.CreateWarehouseRequest;
import com.assignment.warehouse.service.WarehouseService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class WarehouseControllerTest {

    @Mock
    private WarehouseService warehouseService;
    
    @Mock
    private Warehouse warehouse;

    @InjectMocks
    private WarehouseController warehouseController;
    
    @Mock
    private ServletRequestAttributes requestAttributes;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(requestAttributes);
    }

    @Test
    public void testCreateWarehouse_Success() throws Exception {
        CreateWarehouseRequest warehouseRequest = new CreateWarehouseRequest("Warehouse 1");
        Mockito.when(warehouseService.createWarehouse(anyString())).thenReturn(warehouse);
        
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(requestAttributes.getRequest()).thenReturn(mockRequest);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        ResponseEntity<Object> response = warehouseController.createWarehouse(warehouseRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(warehouseService).createWarehouse(anyString());
    }

    @Test
    public void testCreateWarehouse_BadRequest() throws Exception {
    	CreateWarehouseRequest invalidWarehouseRequest = new CreateWarehouseRequest(""); // Empty warehouse name

        assertThrows(IllegalArgumentException.class, () -> warehouseController.createWarehouse(invalidWarehouseRequest));
        Mockito.verifyNoInteractions(warehouseService); 
    }
}
