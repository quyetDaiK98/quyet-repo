package com.personal.requestmanagement.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role implements Serializable, GrantedAuthority {

	@Transient
	private static final long serialVersionUID = -7402837321796905823L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "role_code")
	private String roleCode;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Override
	@Transient
	public String getAuthority() {
		// TODO Auto-generated method stub
		return "ROLE_" + this.roleCode;
	}

}
