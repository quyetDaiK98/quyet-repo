package com.personal.requestmanagement.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.personal.requestmanagement.model.entity.Role;
import com.personal.requestmanagement.model.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private long id;

    private String password;

    private String userName;

    private List<String> roleCodes;
    
    private DepartmentDto department;
    
    private String email;
    
    public UserDto(User entity) {
    	if(entity == null)
    		return;
    	
    	this.id = entity.getId();
    	
    	this.userName = entity.getUsername();
    	
    	if(entity.getRoles() != null && !entity.getRoles().isEmpty()) {
    		List<String> roleCodes = new ArrayList<String>();
    		
    		for (Role role : entity.getRoles()) 
				roleCodes.add(role.getRoleCode());
				
			this.roleCodes = roleCodes;
    	}
    	
    	if(entity.getDepartment() != null) 
    		this.department = new DepartmentDto(entity.getDepartment());
    	
    }
}
