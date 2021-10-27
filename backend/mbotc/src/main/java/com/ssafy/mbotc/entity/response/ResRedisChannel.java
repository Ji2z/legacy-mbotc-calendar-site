package com.ssafy.mbotc.entity.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResRedisChannel {

	String channelId;
	String channelName;
	boolean show;
	
	@Override
	public String toString() {
		return "{\"channelId\":\"" + channelId + "\", \"channelName\":\"" + channelName + "\", \"show\":\"" + show + "\"}";
	}

	public ResRedisChannel() {}

	public ResRedisChannel(String channelId, String channelName, boolean show) {
		super();
		this.channelId = channelId;
		this.channelName = channelName;
		this.show = show;
	}
}
