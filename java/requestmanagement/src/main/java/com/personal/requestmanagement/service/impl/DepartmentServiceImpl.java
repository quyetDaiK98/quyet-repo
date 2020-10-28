package com.personal.requestmanagement.service.impl;

import com.personal.requestmanagement.model.dto.DepartmentDto;
import com.personal.requestmanagement.model.entity.Department;
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
		Department entity = null;
		
		if(dto.getId() > 0)
			entity = departmentRepository.getOne(dto.getId());
		
		if(entity == null)
			entity = new Department();
		
		entity.setDeptCode(dto.getDeptCode());
		entity.setDeptName(dto.getDeptName());
		
		try {
			departmentRepository.save(entity);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
		try {
			departmentRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}
