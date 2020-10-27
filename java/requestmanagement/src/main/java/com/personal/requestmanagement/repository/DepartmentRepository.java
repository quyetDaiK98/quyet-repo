package com.personal.requestmanagement.repository;

import com.personal.requestmanagement.model.dto.DepartmentDto;
import com.personal.requestmanagement.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select new com.personal.requestmanagement.model.dto.DepartmentDto(entity) from Department entity where entity.deptCode like %?1% and entity.deptName like %?2%")
    List<DepartmentDto> findAllDto(String code, String name);
    
    @Query("select new com.personal.requestmanagement.model.dto.DepartmentDto(entity) from Department entity where entity.id =?1")
    DepartmentDto findOneDto(long id);
}
