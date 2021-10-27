package com.ssafy.mbotc.entity.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResRedisTeam {

	String teamId;
	String teamName;
	String color;
	List<ResRedisChannel> subscribe;
	
	@Override
	public String toString() {
		return "{\"teamId\":\"" + teamId + "\", \"teamName\":\"" + teamName + "\", \"color\":\"" + color + "\", \"subscribe\":"
				+ subscribe + "}";
	}
}
