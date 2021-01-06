package com.personal.requestmanagement.service.impl;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.personal.requestmanagement.constant.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal.requestmanagement.model.dto.UserDto;
import com.personal.requestmanagement.model.entity.Role;
import com.personal.requestmanagement.model.entity.User;
import com.personal.requestmanagement.repository.DepartmentRepository;
import com.personal.requestmanagement.repository.RoleRepository;
import com.personal.requestmanagement.repository.UserRepository;
import com.personal.requestmanagement.service.UserService;
import com.personal.requestmanagement.utils.StringUtil;
import org.springframework.web.multipart.MultipartFile;

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

		entity.setPassword(passwordEncoder.encode("123456"));
		entity.setUserName(dto.getUserName());
		entity.setEmail(dto.getEmail());
		entity.setSign(dto.getSign());
		entity.setPhone(dto.getPhone());
		entity.setAddress(dto.getAddress());

//		if(dto.getRoleCodes() != null && dto.getRoleCodes().size() > 0) {
//			Set<Role> roles = new HashSet<>();
//			for (String roleCode : dto.getRoleCodes()) {
//				Role role = roleRepository.findByRoleCode(roleCode);
//				roles.add(role);
//			}
//			entity.setRoles(roles);
//		}

		if(!StringUtil.isEmpty(dto.getRoleCode())) {
			Set<Role> roles = new HashSet<>();
			Role role = roleRepository.findByRoleCode(dto.getRoleCode());
			roles.add(role);
			entity.setRoles(roles);
		}

		if(dto.getDepartment() != null && dto.getDepartment().getId() > 0)
			entity.setDepartment(departmentRepository.getOne(dto.getDepartment().getId()));

		try {
			MultipartFile multipartFile = dto.getMultipartFile();

			if(multipartFile != null && multipartFile.getSize() > 0) {
				entity.setSign(multipartFile.getOriginalFilename());

				String storePath = CommonConst.DOC_STORE_PATH + "\\" + entity.getUserName();
				userRepository.save(entity);
				File signFolder = new File(storePath);
				if(!signFolder.exists())
					signFolder.mkdir();
				multipartFile.transferTo(new File(storePath + "\\" + entity.getSign()));
			}
			userRepository.save(entity);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<UserDto> findAllDto(long deptId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder(
				"select new com.personal.requestmanagement.model.dto.UserDto(entity) from User entity where entity.department != null ");

		if (deptId > 0)
			hql.append(" and entity.department.id = :deptId ");

		if (!StringUtil.isEmpty(userName))
			hql.append(" and entity.userName like CONCAT('%',:userName,'%')");

		try {
			Query query = entityManager.createQuery(hql.toString(), UserDto.class);

			if (deptId > 0)
				query.setParameter("deptId", deptId);

			if (!StringUtil.isEmpty(userName))
				query.setParameter("userName", userName);


			List<UserDto> ret = query.getResultList();
			return ret;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}