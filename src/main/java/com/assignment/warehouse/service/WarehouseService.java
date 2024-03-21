package com.assignment.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.warehouse.entity.Box;
import com.assignment.warehouse.entity.Warehouse;
import com.assignment.warehouse.model.BoxResponse;
import com.assignment.warehouse.repository.WarehouseRepository;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final BoxService boxService;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, BoxService boxService) {
        this.warehouseRepository = warehouseRepository;
        this.boxService = boxService;
    }

    public Warehouse createWarehouse(String warehouseName) {
    	Warehouse warehouse = new Warehouse(warehouseName);
        return warehouseRepository.save(warehouse);
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));
    }
   
	public BoxResponse getBoxInWarehouseById(Long warehouseId, Long boxId) {
		Warehouse warehouse = warehouseRepository.findById(warehouseId)
				                                 .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + warehouseId));
		Box box = warehouse.getBoxes().stream()
				                      .filter(b -> b.getId().equals(boxId)).findFirst()
				                      .orElseThrow(() -> new ResourceNotFoundException("Box not found with id: " + boxId));
		BoxResponse boxResponse = new BoxResponse();
		boxResponse.setId(boxId);
		boxResponse.setWarehouseId(warehouseId);
		boxResponse.setCapacity(box.getCapacity());
		boxResponse.setLocation(box.getLocation());
		return boxResponse;

	}

    public Box createBoxInWarehouse(Long warehouseId, int capacity, String location) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        Box box = new Box(capacity, location);
        box.setWarehouse(warehouse);
        warehouse.addBox(box);
        warehouseRepository.save(warehouse);
        return boxService.createBox(box);
    }
}