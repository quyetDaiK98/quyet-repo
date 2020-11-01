package com.personal.requestmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.requestmanagement.model.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
	
}
