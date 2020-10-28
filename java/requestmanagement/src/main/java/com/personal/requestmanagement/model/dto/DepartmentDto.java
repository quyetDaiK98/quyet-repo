package com.personal.requestmanagement.model.dto;

import com.personal.requestmanagement.model.entity.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDto {
	private long id;

	@NotEmpty(message = "Tên phòng ban không được để trống!")
	private String deptName;

	@NotEmpty(message = "Mã phòng ban không được để trống!")
	private String deptCode;

	public DepartmentDto(Department entity){
		if(entity == null)
			return;
		this.id = entity.getId();
		this.deptCode = entity.getDeptCode();
		this.deptName = entity.getDeptName();
	}
}
