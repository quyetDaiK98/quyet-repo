package com.personal.requestmanagement.service;

import org.springframework.stereotype.Service;

import com.personal.requestmanagement.model.entity.User;

@Service
public interface UserService {
	User getCurrentUser();
}
