package com.ssafy.mbotc.service;

import java.util.List;

import com.ssafy.mbotc.entity.Notice;

public interface NoticeService {

	List<Notice> getNoticeByYearAndMonth(String year, String month, String channelToken);
	Notice findByNoticeId(String postId);
	
}
