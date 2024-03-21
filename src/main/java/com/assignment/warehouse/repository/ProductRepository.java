package com.assignment.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.warehouse.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String searchTerm1, String searchTerm2);
}
