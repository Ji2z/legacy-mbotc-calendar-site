package com.ssafy.mbotc.service;

import java.util.List;

import com.ssafy.mbotc.entity.Notice;

public interface NoticeService {

	List<Notice> findAllByYearAndMonth(String year, String month, String channelId);
	Notice findByNoticeId(String postId);
	
}
