package com.personal.requestmanagement.service.impl;

import com.personal.requestmanagement.model.entity.Role;
import com.personal.requestmanagement.repository.RoleRepository;
import com.personal.requestmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByRoleCode(String roleCode) {
        return roleRepository.findByRoleCode(roleCode);
    }
}
