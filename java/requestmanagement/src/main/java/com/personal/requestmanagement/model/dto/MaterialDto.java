package com.personal.requestmanagement.model.dto;

import com.personal.requestmanagement.model.entity.Material;

public class MaterialDto {
	
	private int id;

	private String matCode;

	private String matName;

	private String unit;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public MaterialDto() {}
	
	public MaterialDto(Material entity) {
		if(entity == null) 
			return;
		this.id = entity.getId();
		this.matCode = entity.getMatCode();
		this.matName = entity.getMatName();
		this.unit = entity.getUnit();
	}
}
