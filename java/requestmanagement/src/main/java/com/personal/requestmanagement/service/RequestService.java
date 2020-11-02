package com.personal.requestmanagement.service;

import java.util.List;

import com.personal.requestmanagement.model.dto.RequestDto;
import com.personal.requestmanagement.model.search.SearchRequest;

public interface RequestService {
	
	List<RequestDto> getAllDto(SearchRequest searchDto);
	
	boolean save(RequestDto dto);
	
	boolean remove(long id);
	
	RequestDto findOneDto(long id);
}
