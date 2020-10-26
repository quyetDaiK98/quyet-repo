package com.personal.requestmanagement.service;

import com.personal.requestmanagement.model.entity.Role;

public interface RoleService {
    Role findByRoleCode(String roleCode);
}
