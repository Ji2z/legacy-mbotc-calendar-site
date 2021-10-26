package com.ssafy.mbotc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mbotc.dao.NoticeRepository;
import com.ssafy.mbotc.entity.Notice;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeRepository noticeRepository;

	@Override
	public List<Notice> findAllByYearAndMonth(String year, String month, String channelId) {
		return noticeRepository.getNoticeByYearAndMonth(year, month, channelId);
	}

	@Override
	public Notice findByNoticeId(String postId) {
		return noticeRepository.findByToken(postId);
	}
	
}
