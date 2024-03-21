package com.assignment.warehouse.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;
    
    public Warehouse() {
    }
    
    public Warehouse(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "warehouse")
    private List<Box> boxes;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public void addBox(Box box) {
    	if (boxes == null) {
            boxes = new ArrayList<>(); 
        }
        boxes.add(box);
    }

    public List<Box> getBoxes() {
    	if (boxes == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(boxes);
        }
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
