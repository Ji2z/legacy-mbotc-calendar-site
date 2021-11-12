package com.ssafy.mbotc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mbotc.entity.Notice;
import com.ssafy.mbotc.entity.request.ReqNoticePost;

public interface NoticeService {

	List<Notice> getNoticeByYearAndMonth(String year, String month, String channelToken);
	List<Notice> getNoticeByYearAndMonthAndDay(String year, String month, String day, String channelToken);
	Notice findByNoticeId(String postId);
	Notice save(Notice notice);
	List<ReqNoticePost> getTodayNoticeList(String channelToken);
	void deleteByToken(String postId);
	List<Notice> getNoticeSearch(String word, List<Long> channelId);
}
