package com.personal.requestmanagement.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

//@Getter
//@Setter
@Entity
@Table(name = "department")
public class Department implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1375732158981268172L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "dept_name")
	private String deptName;
	
	@Column(name = "dept_code")
	private String deptCode;

	@OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
	private Set<User> users;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
