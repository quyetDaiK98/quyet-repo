package com.personal.requestmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.personal.requestmanagement.model.dto.RequestMaterialDto;
import com.personal.requestmanagement.model.entity.Material;
import com.personal.requestmanagement.model.entity.RequestMaterial;
import com.personal.requestmanagement.model.search.SearchRequest;
import com.personal.requestmanagement.repository.MaterialRepository;
import com.personal.requestmanagement.repository.RequestMaterialRepo;
import com.personal.requestmanagement.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.requestmanagement.model.dto.RequestDto;
import com.personal.requestmanagement.model.entity.Request;
import com.personal.requestmanagement.repository.RequestRepository;
import com.personal.requestmanagement.repository.UserRepository;
import com.personal.requestmanagement.service.RequestService;
import com.personal.requestmanagement.utils.DateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestRepository requestRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EntityManager entityManager;

	@Autowired
	RequestMaterialRepo requestMaterialRepo;

	@Autowired
	MaterialRepository materialRepository;

	@Override
	@SuppressWarnings("unchecked")
	public List<RequestDto> getAllDto(SearchRequest searchDto) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder(
				"select new com.personal.requestmanagement.model.dto.RequestDto(entity) from Request entity where entity.status != 6 ");

		if (searchDto.getType() > 0)
			hql.append(" and entity.type = :type ");

		if (searchDto.getStatus() > 0)
			hql.append(" and entity.status = :status ");

		if (!StringUtil.isEmpty(searchDto.getFromDate()))
			hql.append(" and DATE(entity.createdDate) >= DATE(:fromDate) ");

		if (!StringUtil.isEmpty(searchDto.getToDate()))
			hql.append(" and DATE(entity.createdDate) <= DATE(:toDate) ");

		if (searchDto.getRole().equals("ROLE_EMP"))
			hql.append(" and entity.user.id = :userId");

		if (searchDto.getRole().equals("ROLE_MANAGER"))
			hql.append(" and entity.user.department.id = :deptId and entity.status in (1,2,4,5) ");

		if (searchDto.getRole().equals("ROLE_OPERATOR"))
			hql.append(" and entity.status in (2,3,5) ");

		hql.append(" order by entity.createdDate desc");

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

	@Override
	public RequestDto save(RequestDto dto) {
		// TODO Auto-generated method stub
		Request entity = null;
		if (dto.getId() > 0)
			entity = requestRepository.getOne(dto.getId());
		if (entity == null) {
			entity = new Request();
			entity.setCreatedDate(DateUtil.now());
		}
		entity.setReason(dto.getReason());
		entity.setStatus(dto.getStatus());
		entity.setType(dto.getType());

//		if(dto.getRequestMaterialDtos() != null && dto.getRequestMaterialDtos().size() > 0){
//			List<RequestMaterial> requestMaterials = new ArrayList<>();
//			for (RequestMaterialDto requestMaterialDto : dto.getRequestMaterialDtos()){
//				RequestMaterial requestMaterial = new RequestMaterial();
//
//				Material material = new Material();
//				material.setId(requestMaterialDto.getMaterial().getId());
//				requestMaterial.setMaterial(material);
//
//				requestMaterials.add(requestMaterial);
//			}
//			entity.setMaterial(requestMaterials);
//		}

		if(dto.getMatIds() != null && dto.getMatIds().size() > 0){
			List<RequestMaterial> requestMaterials = new ArrayList<>();

			for (int i = 0; i < dto.getMatIds().size(); i++) {
				RequestMaterial requestMaterial = new RequestMaterial();

				requestMaterial.setMaterial(materialRepository.getOne(dto.getMatIds().get(i)));

				requestMaterial.setQuantity(dto.getQuantities().get(i));
				requestMaterial.setRequest(entity);
				requestMaterials.add(requestMaterialRepo.save(requestMaterial));
			}

			entity.setMaterial(requestMaterials);
		}

		try {
			entity.setUser(userRepository.getOne(dto.getUser().getId()));
			entity.setFromDate(DateUtil.stringToDate(dto.getFromDate(), DateUtil.FORMAT_DDMMYYYY_HHMM));
			entity.setToDate(DateUtil.stringToDate(dto.getToDate(), DateUtil.FORMAT_DDMMYYYY_HHMM));

			entity = requestRepository.save(entity);

			return new RequestDto(entity);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean remove(long id) {
		// TODO Auto-generated method stub
		try {
			requestRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public RequestDto findOneDto(long id) {
		// TODO Auto-generated method stub
		Request entity = requestRepository.getOne(id);
		return new RequestDto(entity);
	}

	@Override
	public boolean changeStatus(int reqId, int status) {
		try {
			Request entity = requestRepository.getOne((long) reqId);
			if(entity != null)
				entity.setStatus(status);
			requestRepository.save(entity);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

}
