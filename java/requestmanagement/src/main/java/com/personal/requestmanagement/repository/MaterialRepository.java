package com.personal.requestmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.personal.requestmanagement.model.dto.MaterialDto;
import com.personal.requestmanagement.model.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
	@Query("select new com.personal.requestmanagement.model.dto.MaterialDto(entity) from Material entity")
    List<MaterialDto> findAllDto();

    @Query("select new com.personal.requestmanagement.model.dto.MaterialDto(entity) from Material entity where entity.id = ?1")
    MaterialDto findDtoById(int id);
}
