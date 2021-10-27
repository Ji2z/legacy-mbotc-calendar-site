package com.ssafy.mbotc.entity;

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
@Table(name = "channel")
public class Channel extends BaseEntity {
	
	@ManyToOne(cascade = CascadeType.MERGE)
	Team team;
	
	@Column
	String name;
	
	@Column
	String token;

	public Channel() {}
	
	public Channel(Team team, String name, String token) {
		super();
		this.team = team;
		this.name = name;
		this.token = token;
	}

	
}
