package com.personal.requestmanagement.service;

import com.personal.requestmanagement.model.dto.UserDto;
import com.personal.requestmanagement.model.entity.User;

public interface UserService {
	User getCurrentUser();

	boolean save(UserDto dto);
}
