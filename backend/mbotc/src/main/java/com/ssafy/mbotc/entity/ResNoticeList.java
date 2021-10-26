package com.ssafy.mbotc.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResNoticeList {

	String subscribe;
	List<Notice> notifications;
	
}
