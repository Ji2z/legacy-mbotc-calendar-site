package com.ssafy.mbotc.entity.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResRedisUser {

	List<ResRedisTeam> teams;
	String theme;
	
	@Override
	public String toString() {
		return "{\"teams\":" + teams + ", \"theme\":\"" + theme + "\"}";
	}
	
}
