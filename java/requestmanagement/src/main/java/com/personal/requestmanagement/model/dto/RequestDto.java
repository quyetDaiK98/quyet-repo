package com.personal.requestmanagement.model.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.personal.requestmanagement.model.entity.Request;
import com.personal.requestmanagement.utils.DateUtil;
import com.personal.requestmanagement.validate.annotation.DateConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@DateConstraint(fromDate = "fromDate", toDate = "toDate")
public class RequestDto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	private int status;

    private UserDto user;
    
    private int type;
    
    @NotEmpty(message = "Lý do không được để trống !")
    private String reason;
    
    private String fromDate;
    
    private String toDate;
    
    private String createdDate;
    
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
    		this.toDate = DateUtil.getDateByFormat(entity.getToDate(), DateUtil.FORMAT_DDMMYYYY_HHMMSS);
    	if(entity.getCreatedDate() != null)
    		this.createdDate = DateUtil.getDateByFormat(entity.getCreatedDate(), DateUtil.FORMAT_DDMMYYYY_HHMMSS);
    	
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
