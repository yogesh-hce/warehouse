package com.assignment.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.warehouse.entity.Box;

public interface BoxRepository extends JpaRepository<Box, Long> {

    List<Box> findByWarehouseId(Long warehouseId);
}
