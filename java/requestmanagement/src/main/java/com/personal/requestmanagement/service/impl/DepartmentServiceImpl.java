package com.personal.requestmanagement.service.impl;

import com.personal.requestmanagement.model.dto.DepartmentDto;
import com.personal.requestmanagement.repository.DepartmentRepository;
import com.personal.requestmanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findAllDto(String code, String name) {
    	List<DepartmentDto> ret = departmentRepository.findAllDto(code, name);
        return ret;
    }

	@Override
	public boolean save(DepartmentDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DepartmentDto getOneDto(long id) {
		// TODO Auto-generated method stub
		DepartmentDto ret = departmentRepository.findOneDto(id);
		return ret;
	}

	@Override
	public boolean remove(long id) {
		// TODO Auto-generated method stub
		return false;
	}
}
