package com.assignment.warehouse.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assignment.warehouse.entity.Box;
import com.assignment.warehouse.model.BoxResponse;
import com.assignment.warehouse.model.CreateBoxRequest;
import com.assignment.warehouse.service.WarehouseService;

@RestController
@RequestMapping("/api/warehouses/{warehouseId}/boxes")
@CrossOrigin(origins = "*")
public class BoxController {

	private final WarehouseService warehouseService;

	@Autowired
    public BoxController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

	@PostMapping
    public ResponseEntity<Object> createBoxInWarehouse(@PathVariable Long warehouseId, @RequestBody CreateBoxRequest request) {
		if (request.getCapacity() <= 0) {
            throw new IllegalArgumentException("Box capacity must be a positive integer");
        }
        if (request.getLocation() == null || request.getLocation().isEmpty()) {
            throw new IllegalArgumentException("Box location cannot be empty");
        }
        Box createdBox = warehouseService.createBoxInWarehouse(warehouseId, request.getCapacity(), request.getLocation());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdBox.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{boxId}")
    public ResponseEntity<BoxResponse> getBoxById(@PathVariable Long warehouseId, @PathVariable Long boxId) {
    	BoxResponse boxResponse = warehouseService.getBoxInWarehouseById(warehouseId, boxId);
        return ResponseEntity.ok(boxResponse);
    }
}