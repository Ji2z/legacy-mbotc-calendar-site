package com.ssafy.mbotc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
	
	@Column
	String userEmail;
	
	@Column
	String userName;
	
	@Column
	String url;
	
	@Column
	String token;
	
	@Column
	String userId;

	@Override
	public String toString() {
		return "User [userEmail=" + userEmail + ", userName=" + userName + ", url=" + url + ", token=" + token + ", id="
				+ id + "]";
	}
	
}
