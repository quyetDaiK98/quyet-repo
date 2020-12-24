package com.personal.requestmanagement.service;

import com.personal.requestmanagement.model.dto.UserDto;
import com.personal.requestmanagement.model.entity.User;

import java.util.List;

public interface UserService {
	User getCurrentUser();

	boolean save(UserDto dto);

	List<UserDto> findAllDto(long deptId, String userName);

}
