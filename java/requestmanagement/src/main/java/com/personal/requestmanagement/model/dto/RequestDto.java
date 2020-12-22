package com.personal.requestmanagement.model.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.personal.requestmanagement.model.entity.Request;
import com.personal.requestmanagement.model.entity.RequestMaterial;
import com.personal.requestmanagement.utils.DateUtil;
import com.personal.requestmanagement.validate.annotation.DateConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
//@NoArgsConstructor
@DateConstraint(fromDate = "fromDate", toDate = "toDate")
public class RequestDto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	private int status;

    private UserDto user;

    private int type;

    private List<Integer> matIds;

	private List<Integer> quantities;

	@NotEmpty(message = "Lý do không được để trống !")
    private String reason;
    
    @NotEmpty(message = "Thời gian từ không được để trống !")
    private String fromDate;
    
    @NotEmpty(message = "Thời gian đến được để trống !")
    private String toDate;
    
    private String createdDate;

    private String typeStr;

    private String statusStr;

    private List<RequestMaterialDto> requestMaterialDtos;

	public String getTypeStr() {
		switch (this.type){
			case 1:
				return "Đề nghị xin nghỉ phép";
			case 2:
				return "Đề nghị mua vật tư";
			default:
				return "";
		}
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getStatusStr() {
		switch (this.status){
			case 1:
				return "Dự thảo";
			case 2:
				return "Đã duyệt";
			case 3:
				return "Đã xử lý";
			case 4:
				return "Từ chối";
			case 5:
				return "Hoàn thành";
			case 6:
				return "Đã hủy";
			default:
				return "";
		}
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<RequestMaterialDto> getRequestMaterialDtos() {
		return requestMaterialDtos;
	}

	public void setRequestMaterialDtos(List<RequestMaterialDto> requestMaterialDtos) {
		this.requestMaterialDtos = requestMaterialDtos;
	}

	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public String getReason() {
		return reason;
	}

	public List<Integer> getMatIds() {
		return matIds;
	}

	public void setMatIds(List<Integer> matIds) {
		this.matIds = matIds;
	}

	public List<Integer> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Integer> quantities) {
		this.quantities = quantities;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}



	public String getFromDate() {
		return fromDate;
	}



	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}



	public String getToDate() {
		return toDate;
	}



	public void setToDate(String toDate) {
		this.toDate = toDate;
	}



	public String getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public RequestDto() {
		super();
	}

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

    	if(entity.getMaterial() != null && entity.getMaterial().size() > 0){
    		List<RequestMaterialDto> requestMaterialDtos = new ArrayList<>();

			for (int i = 0; i < entity.getMaterial().size(); i++) {
				RequestMaterialDto requestMaterialDto = new RequestMaterialDto(entity.getMaterial().get(i));
				requestMaterialDto.setIndex(i + 1);
				requestMaterialDtos.add(requestMaterialDto);
			}

			this.requestMaterialDtos = requestMaterialDtos;
		}
    }
}
