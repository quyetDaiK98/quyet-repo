package com.personal.requestmanagement.repository;

import com.personal.requestmanagement.model.dto.UserDto;
import com.personal.requestmanagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUserName(String userName);

	@Query("select new com.personal.requestmanagement.model.dto.UserDto(entity) from User entity where entity.department.id = ?1 and entity.userName like %?2%")
	List<UserDto> findAllDto(long deptId, String userName);

	@Query("select new com.personal.requestmanagement.model.dto.UserDto(entity) from User entity where entity.id =?1")
	UserDto findOneDto(long id);
}
