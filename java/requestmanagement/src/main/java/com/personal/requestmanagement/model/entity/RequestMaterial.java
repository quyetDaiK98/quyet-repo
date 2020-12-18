package com.personal.requestmanagement.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "request_material")
public class RequestMaterial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 249325665143708186L;

	private Request request;
	
	private Material material;
	
	private int quantity;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
