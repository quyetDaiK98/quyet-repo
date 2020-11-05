package com.personal.requestmanagement.service.impl;

import com.personal.requestmanagement.model.dto.UserDto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal.requestmanagement.model.entity.Role;
import com.personal.requestmanagement.model.entity.User;
import com.personal.requestmanagement.repository.DepartmentRepository;
import com.personal.requestmanagement.repository.RoleRepository;
import com.personal.requestmanagement.repository.UserRepository;
import com.personal.requestmanagement.service.UserService;

@Service("customUserDetailService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByUserName(username);
		
		return user;
	}

	@Override
	public User getCurrentUser() {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}

	@Override
	public boolean save(UserDto dto) {
		if(dto == null)
			return false;
		
		User entity = null;
		if(dto.getId() > 0)
			entity = userRepository.findById(dto.getId()).orElse(null);
		if(entity == null) {
			entity = new User();
		}
		
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity.setUserName(dto.getUserName());
		entity.setEmail(dto.getEmail());
		
		if(dto.getRoleCodes() != null && dto.getRoleCodes().size() > 0) {
			Set<Role> roles = new HashSet<>();
			for (String roleCode : dto.getRoleCodes()) {
				Role role = roleRepository.findByRoleCode(roleCode);
				roles.add(role);
			}
			entity.setRoles(roles);
		}
		if(dto.getDepartment() != null && dto.getDepartment().getId() > 0)
			entity.setDepartment(departmentRepository.getOne(dto.getDepartment().getId()));
		
		try {
			userRepository.save(entity);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}

}
