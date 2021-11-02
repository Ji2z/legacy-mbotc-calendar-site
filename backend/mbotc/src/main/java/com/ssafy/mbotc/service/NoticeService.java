package com.ssafy.mbotc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mbotc.entity.Notice;

public interface NoticeService {

	List<Notice> getNoticeByYearAndMonth(String year, String month, String channelToken);
	List<Notice> getNoticeByYearAndMonthAndDay(String year, String month, String day, String channelToken);
	Notice findByNoticeId(String postId);
	//String uploadFileToMM(MultipartFile file, String url, String token, String channelId);
	
}
