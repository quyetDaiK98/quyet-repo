package com.personal.requestmanagement.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.personal.requestmanagement.model.entity.Role;
import com.personal.requestmanagement.model.entity.User;

//@Getter
//@Setter
//@NoArgsConstructor
public class UserDto {
    private long id;

    private String password;

    private String userName;

    private List<String> roleCodes;

    private String roleCode;
    
    private DepartmentDto department;
    
    private String email;
    
    private String sign;

    private String address;
    
    private String phone;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public List<String> getRoleCodes() {
		return roleCodes;
	}



	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}



	public DepartmentDto getDepartment() {
		return department;
	}



	public void setDepartment(DepartmentDto department) {
		this.department = department;
	}



	public String getEmail() {
		return email;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDto() {
		super();
	}

	public UserDto(User entity) {
    	if(entity == null)
    		return;
    	
    	this.id = entity.getId();
    	
    	this.userName = entity.getUsername();
    	
    	this.sign = entity.getSign();
    	this.phone = entity.getPhone();
    	this.address = entity.getAddress();
    	this.email = entity.getEmail();
    	
    	if(entity.getRoles() != null && !entity.getRoles().isEmpty()) {
    		List<String> roleCodes = new ArrayList<String>();
    		
    		for (Role role : entity.getRoles()) 
				roleCodes.add(role.getRoleCode());
				
			this.roleCodes = roleCodes;
			this.roleCode = roleCodes.get(0);
    	}
    	
    	if(entity.getDepartment() != null) 
    		this.department = new DepartmentDto(entity.getDepartment());
    	
    }
}
