package com.assignment.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.warehouse.entity.Box;
import com.assignment.warehouse.repository.BoxRepository;

@Service
public class BoxService {

    private final BoxRepository boxRepository;

    @Autowired
    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public Box createBox(Box box) {
        return boxRepository.save(box);
    }

    public Box getBoxById(Long id) {
        return boxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Box not found with id: " + id));
    }

    public Box updateBox(Long id, Box box) {
        box.setId(id);
        return boxRepository.save(box);
    }

    public void deleteBox(Long id) {
        boxRepository.deleteById(id);
    }

    public List<Box> findBoxesInWarehouse(Long warehouseId) {
        return boxRepository.findByWarehouseId(warehouseId);
    }
}
