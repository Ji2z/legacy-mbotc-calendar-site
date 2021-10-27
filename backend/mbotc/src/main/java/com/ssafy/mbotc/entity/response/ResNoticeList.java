package com.ssafy.mbotc.entity.response;

import java.util.List;

import com.ssafy.mbotc.entity.Notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResNoticeList {

	String subscribe;
	List<Notice> notifications;
	
}
