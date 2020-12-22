package com.personal.requestmanagement.model.dto;

import com.personal.requestmanagement.model.entity.RequestMaterial;

public class RequestMaterialDto {
	private int index;

	private int id;
	
	private MaterialDto material;
	
	private RequestDto request;
	
	private int quantity;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MaterialDto getMaterial() {
		return material;
	}

	public void setMaterial(MaterialDto material) {
		this.material = material;
	}

	public RequestDto getRequest() {
		return request;
	}

	public void setRequest(RequestDto request) {
		this.request = request;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public RequestMaterialDto() {}
	
	public RequestMaterialDto(RequestMaterial entity) {
		if(entity == null)
			return;
		
		this.id = entity.getId();
		this.quantity = entity.getId();
		
		if(entity.getRequest() != null) {
			RequestDto requestDto = new RequestDto();
			requestDto.setId(entity.getRequest().getId());
			this.request = requestDto;
		}
		
		if(entity.getMaterial() != null)
			this.material = new MaterialDto(entity.getMaterial());
	}
}
