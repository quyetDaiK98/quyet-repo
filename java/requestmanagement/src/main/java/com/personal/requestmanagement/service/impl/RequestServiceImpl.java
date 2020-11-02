package com.personal.requestmanagement.service.impl;

import java.util.List;

import com.personal.requestmanagement.model.search.SearchRequest;
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

@Service
public class RequestServiceImpl implements RequestService {
	
	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	EntityManager entityManager;

	@Override
	public List<RequestDto> getAllDto(SearchRequest searchDto) {
		// TODO Auto-generated method stub
		String hql = "select new com.personal.requestmanagement.model.dto.RequestDto(entity) from Request entity where 1=1 ";

		if(searchDto.getType() > 0)
			hql += " and entity.type = :type ";

		if(searchDto.getStatus() > 0)
			hql += " and entity.status = :status ";

		if(!StringUtil.isEmpty(searchDto.getFromDate()))
			hql += " and entity.fromDate >= :fromDate ";

		if(!StringUtil.isEmpty(searchDto.getToDate()))
			hql += " and entity.toDate <= :toDate ";

		try {
			Query query = entityManager.createQuery(hql, RequestDto.class);

			if(searchDto.getType() > 0)
				query.setParameter("type", searchDto.getType());

			if(searchDto.getStatus() > 0)
				query.setParameter("status", searchDto.getStatus());

			if(!StringUtil.isEmpty(searchDto.getFromDate()))
				query.setParameter("fromDate", DateUtil.stringToDate(searchDto.getFromDate(), DateUtil.FORMAT_DDMMYYYY_HHMM));


			if(!StringUtil.isEmpty(searchDto.getToDate()))
				query.setParameter("toDate", DateUtil.stringToDate(searchDto.getToDate(), DateUtil.FORMAT_DDMMYYYY_HHMM));

			List<RequestDto> ret = query.getResultList();
			return ret;
		}catch (Exception exception){

		}
		return null;
	}

	@Override
	public boolean save(RequestDto dto) {
		// TODO Auto-generated method stub
		Request entity = null;
		if(dto.getId() > 0)
			entity = requestRepository.getOne(dto.getId());
		if(entity == null) {
			entity = new Request();
			entity.setCreatedDate(DateUtil.now());
		}
		entity.setReason(dto.getReason());
		entity.setStatus(dto.getStatus());
		entity.setType(dto.getType());
		
		try {
			entity.setUser(userRepository.getOne(dto.getUser().getId()));
			entity.setFromDate(DateUtil.stringToDate(dto.getFromDate(), DateUtil.FORMAT_DDMMYYYY_HHMM));
			entity.setToDate(DateUtil.stringToDate(dto.getToDate(), DateUtil.FORMAT_DDMMYYYY_HHMM));
			requestRepository.save(entity);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}

	@Override
	public boolean remove(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestDto findOneDto(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
