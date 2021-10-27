package com.ssafy.mbotc.entity.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResRedisCheckList {

	public class Post{
		String postId;
		boolean isDone;
	}
	
	List<Post> posts;
}
