package com.personal.requestmanagement.repository;

import com.personal.requestmanagement.model.dto.DepartmentDto;
import com.personal.requestmanagement.model.dto.RequestMaterialDto;
import com.personal.requestmanagement.model.entity.Material;
import com.personal.requestmanagement.model.entity.RequestMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestMaterialRepo extends JpaRepository<RequestMaterial, Integer> {
    @Query("select new com.personal.requestmanagement.model.dto.RequestMaterialDto(entity) from RequestMaterial entity where entity.request.id = ?1")
    List<RequestMaterialDto> findAllDto(long id);

}
