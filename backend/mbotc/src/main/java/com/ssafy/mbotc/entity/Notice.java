package com.ssafy.mbotc.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notice")
public class Notice extends BaseEntity {
	
	@ManyToOne(cascade = CascadeType.MERGE)
	Channel channel;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	User user;
	
	@Column
	Date time;
	
	@Column
	String content;
	
	@Column
	Date startTime;
	
	@Column
	Date endTime;
	
	@Column
	String files;
	
	@Column
	String token;

}
