package com.assignment.warehouse.model;

import java.util.ArrayList;
import java.util.List;

public class SearchProductResponse {
	
	private List<ProductResponse> products;
	
	public List<ProductResponse> getProducts() {
		return products;
	}

	public void addProduct(ProductResponse product) {
		if (products == null) {
			products = new ArrayList<>(); 
        }
		products.add(product);
	}

	public class ProductResponse {
		private String name;
		private String description;
		private String location;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}		
	}
}
