package com.personal.requestmanagement.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
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

}
