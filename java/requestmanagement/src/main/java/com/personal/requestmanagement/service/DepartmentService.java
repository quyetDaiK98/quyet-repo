package com.personal.requestmanagement.service;

import com.personal.requestmanagement.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
	
    List<DepartmentDto> findAllDto(String code, String name);
    
    boolean save(DepartmentDto dto);
    
    DepartmentDto getOneDto(long id);
    
    boolean remove(long id);
}
