package com.ssafy.mbotc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mbotc.dao.NoticeRepository;
import com.ssafy.mbotc.entity.Notice;

@Service("userService")
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeRepository noticeRepository;

	@Override
	public Optional<List<Notice>> findByYearAndMonth(String year, String month, String channelId) {
		return noticeRepository.findByYearAndMonth(year, month, channelId);
	}
	
	

}
