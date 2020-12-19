package com.personal.requestmanagement.model.pdf;

import java.util.List;

import com.personal.requestmanagement.model.dto.RequestMaterialDto;

public class Template {
	
	private List<PdfObject> data;
	
	private List<RequestMaterialDto> dtos;

	public List<PdfObject> getData() {
		return data;
	}

	public void setData(List<PdfObject> data) {
		this.data = data;
	}

	public List<RequestMaterialDto> getDtos() {
		return dtos;
	}

	public void setDtos(List<RequestMaterialDto> dtos) {
		this.dtos = dtos;
	}
	
}
