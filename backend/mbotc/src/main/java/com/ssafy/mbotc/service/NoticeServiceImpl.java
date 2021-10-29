package com.ssafy.mbotc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mbotc.dao.ChannelRepository;
import com.ssafy.mbotc.dao.NoticeRepository;
import com.ssafy.mbotc.entity.Notice;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	ChannelRepository channelRepository;

	@Override
	public List<Notice> getNoticeByYearAndMonth(String year, String month, String channelToken) {
		long channelId = channelRepository.findByToken(channelToken).get().getId();
		return noticeRepository.findAllByYearAndMonth(year+"-"+month, channelId);
	}

	@Override
	public Notice findByNoticeId(String postId) {
		return noticeRepository.findByToken(postId);
	}

	@Override
	public List<Notice> getNoticeByYearAndMonthAndDay(String year, String month, String day, String channelToken) {
		long channelId = channelRepository.findByToken(channelToken).get().getId();
		return noticeRepository.findAllByYearAndMonthAndDay(year+"-"+month+"-"+day, channelId);
	}
	
}
