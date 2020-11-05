package com.personal.requestmanagement.model.search;

import com.personal.requestmanagement.validate.annotation.DateConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@DateConstraint(fromDate = "fromDate", toDate = "toDate")
public class SearchRequest {
	private int type;
	
	private int status;
	
	private String fromDate;
	
	private String toDate;

	private long userId;

	private String role;

	private long deptId;
}
