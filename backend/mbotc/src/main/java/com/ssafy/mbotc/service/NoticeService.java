package com.ssafy.mbotc.service;

import java.util.List;

import com.ssafy.mbotc.entity.Notice;

public interface NoticeService {

	List<Notice> getNoticeByYearAndMonth(String year, String month, String channelToken);
	List<Notice> getNoticeByYearAndMonthAndDay(String year, String month, String day, String channelToken);
	Notice findByNoticeId(String postId);
	
}
