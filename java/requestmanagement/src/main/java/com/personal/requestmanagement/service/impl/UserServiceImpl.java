package com.personal.requestmanagement.service.impl;

import com.personal.requestmanagement.model.dto.RequestDto;
import com.personal.requestmanagement.model.dto.UserDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.personal.requestmanagement.utils.DateUtil;
import com.personal.requestmanagement.utils.StringUtil;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	@Autowired
	EntityManager entityManager;
	

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
		entity.setSign(dto.getSign());
		entity.setPhone(dto.getPhone());
		
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

	@Override
	public List<UserDto> findAllDto(long deptId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder(
				"select new com.personal.requestmanagement.model.dto.UserDto(entity) from User entity where 1=1 ");



		if (deptId > 0)
			hql.append(" and entity.department.id = :deptId ");

		if (!StringUtil.isEmpty(userName))
			hql.append(" and entity.userName like  ");

		try {
			Query query = entityManager.createQuery(hql.toString(), RequestDto.class);

			if (searchDto.getType() > 0)
				query.setParameter("type", searchDto.getType());

			if (searchDto.getStatus() > 0)
				query.setParameter("status", searchDto.getStatus());

			if (!StringUtil.isEmpty(searchDto.getFromDate()))
				query.setParameter("fromDate",
						DateUtil.stringToDate(searchDto.getFromDate(), DateUtil.FORMAT_DDMMYYYY_HHMM));

			if (!StringUtil.isEmpty(searchDto.getToDate()))
				query.setParameter("toDate",
						DateUtil.stringToDate(searchDto.getToDate(), DateUtil.FORMAT_DDMMYYYY_HHMM));

			if (searchDto.getRole().equals("ROLE_EMP"))
				query.setParameter("userId", searchDto.getUserId());

			if (searchDto.getRole().equals("ROLE_MANAGER"))
				query.setParameter("deptId", searchDto.getDeptId());

			// if(searchDto.getRole().equals("ROLE_OPERATOR"))
			// query.setParameter("status", 2);

			List<RequestDto> ret = query.getResultList();
			return ret;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
