package com.assignment.warehouse.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assignment.warehouse.entity.Warehouse;
import com.assignment.warehouse.model.CreateWarehouseRequest;
import com.assignment.warehouse.service.WarehouseService;

@RestController
@RequestMapping("/api/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<Object> createWarehouse(@RequestBody CreateWarehouseRequest warehouseRequest) {
    	if (warehouseRequest.getName() == null || warehouseRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty");
        }
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouseRequest.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdWarehouse.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
