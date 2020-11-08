package com.personal.requestmanagement.model.dto;

import com.personal.requestmanagement.model.entity.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

//@Getter
//@Setter
//@NoArgsConstructor
public class DepartmentDto {
	private long id;

	@NotEmpty(message = "Tên phòng ban không được để trống!")
	private String deptName;

	@NotEmpty(message = "Mã phòng ban không được để trống!")
	private String deptCode;

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getDeptName() {
		return deptName;
	}



	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}



	public String getDeptCode() {
		return deptCode;
	}



	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public DepartmentDto() {
		super();
	}

	public DepartmentDto(Department entity){
		if(entity == null)
			return;
		this.id = entity.getId();
		this.deptCode = entity.getDeptCode();
		this.deptName = entity.getDeptName();
	}
}
