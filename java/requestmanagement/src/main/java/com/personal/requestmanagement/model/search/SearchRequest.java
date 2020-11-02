package com.personal.requestmanagement.model.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequest {
	private int type;
	
	private int status;
	
	private String fromDate;
	
	private String toDate;
}
