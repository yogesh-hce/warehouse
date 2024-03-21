package com.assignment.warehouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.warehouse.entity.Box;
import com.assignment.warehouse.entity.Warehouse;
import com.assignment.warehouse.model.BoxResponse;
import com.assignment.warehouse.repository.BoxRepository;
import com.assignment.warehouse.repository.WarehouseRepository;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;
    
    @Mock
    private BoxRepository boxRepository;

    @Mock
    private BoxService boxService;

    @InjectMocks
    private WarehouseService warehouseService;

    @Test
    public void testCreateWarehouse() {
        String warehouseName = "Main Warehouse";
        warehouseService.createWarehouse(warehouseName);

        // Verify save is called on the repository
        ArgumentCaptor<Warehouse> warehouseCaptor = ArgumentCaptor.forClass(Warehouse.class);
        Mockito.verify(warehouseRepository).save(warehouseCaptor.capture());
    }

    @Test
    public void testGetBoxInWarehouseById_Found() {
        Long warehouseId = 1L;
        Long boxId = 1L;
        Warehouse expectedWarehouse = new Warehouse("Warehouse 1");
        expectedWarehouse.setId(1L);
        Box box = new Box();
        box.setId(boxId);
        List<Box> boxs = new ArrayList<>();
        boxs.add(box);
        expectedWarehouse.setBoxes(boxs);
        Mockito.when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(expectedWarehouse));

        BoxResponse boxResponse = warehouseService.getBoxInWarehouseById(warehouseId, boxId);

        assertEquals(expectedWarehouse.getId(), boxResponse.getWarehouseId());
        Mockito.verify(warehouseRepository).findById(warehouseId);
    }

    @Test
    public void testCreateBoxInWarehouse() {
        Long warehouseId = 1L;
        int capacity = 10;
        String location = "Aisle 1";
        Box expectedBox = new Box(capacity, location);

        Mockito.when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(new Warehouse()));
        Mockito.when(boxService.createBox(any(Box.class))).thenReturn(expectedBox);

        Box createdBox = warehouseService.createBoxInWarehouse(warehouseId, capacity, location);

        assertEquals(expectedBox, createdBox);
        Mockito.verify(warehouseRepository).findById(warehouseId);
        Mockito.verify(boxService).createBox(any(Box.class));
    }
}