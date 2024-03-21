package com.assignment.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.warehouse.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

	
}
