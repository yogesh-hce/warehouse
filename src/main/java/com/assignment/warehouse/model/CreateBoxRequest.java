
package com.assignment.warehouse.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBoxRequest {
	
	private int capacity;
	private String location;
	
	@JsonCreator
	public CreateBoxRequest(@JsonProperty("capacity") int capacity, @JsonProperty("location") String location) {
		this.capacity = capacity;
		this.location = location;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
}