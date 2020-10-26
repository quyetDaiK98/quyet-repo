package com.personal.requestmanagement.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

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
	
	@Column(name = "deptName")
	private String deptName;
	
	@Column(name = "deptCode")
	private String deptCode;

}
