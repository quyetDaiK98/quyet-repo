package com.personal.requestmanagement.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "request")
public class Request implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9156953983269558046L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@Column(name = "status")
	private int status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "type")
    private int type;
    
    @Column(name = "reason")
    private String reason;
    
    @Column(name = "fromDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    
    @Column(name = "toDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;


}
