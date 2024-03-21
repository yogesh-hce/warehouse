package com.assignment.warehouse.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.assignment.warehouse.entity.Box;
import com.assignment.warehouse.model.CreateBoxRequest;
import com.assignment.warehouse.service.WarehouseService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class BoxControllerTest {

    @Mock
    private WarehouseService warehouseService;

    @InjectMocks
    private BoxController boxController;
    
    @Mock
    private ServletRequestAttributes requestAttributes;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(requestAttributes);
    }

    @Test
    public void testCreateBox_Success() throws Exception {
        Long warehouseId = 1L;
        int capacity = 30;
        String location = "Rack 1";
        CreateBoxRequest createBoxRequest = new CreateBoxRequest(capacity, location);
        Box expectedBox = new Box(capacity, location);

        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(requestAttributes.getRequest()).thenReturn(mockRequest);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Mockito.when(warehouseService.createBoxInWarehouse(warehouseId, capacity, location)).thenReturn(expectedBox);
        
        ResponseEntity<Object> response = boxController.createBoxInWarehouse(warehouseId, createBoxRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(warehouseService).createBoxInWarehouse(warehouseId, capacity, location);
    }

    @Test
    public void testCreateBox_BadRequest() throws Exception {
        Long warehouseId = 1L;
        CreateBoxRequest invalidRequest = new CreateBoxRequest(0, null); // Invalid capacity and location
        
        assertThrows(IllegalArgumentException.class, () -> boxController.createBoxInWarehouse(warehouseId, invalidRequest));
        Mockito.verifyNoInteractions(warehouseService); // Service not called due to bad request
    }
}
