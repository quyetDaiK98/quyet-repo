package com.personal.requestmanagement.model.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.personal.requestmanagement.model.entity.Request;
import com.personal.requestmanagement.utils.DateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestDto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	private int status;

    private UserDto user;
    
    private int type;
    
    private String reason;
    
    private String fromDate;
    
    private String toDate;
    
    public RequestDto(Request entity) {
    	if(entity == null)
    		return;
    	this.id = entity.getId();
    	this.status = entity.getStatus();
    	this.type = entity.getType();
    	this.reason = entity.getReason();
    	if(entity.getFromDate() != null)
    		this.fromDate = DateUtil.getDateByFormat(entity.getFromDate(), DateUtil.FORMAT_DDMMYYYY_HHMMSS);
    	if(entity.getToDate() != null)
    		this.fromDate = DateUtil.getDateByFormat(entity.getToDate(), DateUtil.FORMAT_DDMMYYYY_HHMMSS);
    	if(entity.getUser() != null) {
    		UserDto userDto = new UserDto();
    		
    		userDto.setUserName(entity.getUser().getUsername());
    		userDto.setId(entity.getUser().getId());
    		if(entity.getUser().getDepartment() != null) {
    			DepartmentDto departmentDto = new DepartmentDto();
    			departmentDto.setId(entity.getUser().getDepartment().getId());
    			departmentDto.setDeptName(entity.getUser().getDepartment().getDeptName());
    			userDto.setDepartment(departmentDto);
    		}
    		this.user = userDto;
    	}
    }
}
