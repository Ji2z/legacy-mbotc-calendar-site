package com.ssafy.mbotc.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.mbotc.entity.Notice;
import com.ssafy.mbotc.entity.User;

public interface NoticeService {

	Optional<List<Notice>> findByYearAndMonth(String year, String month, String channelId);
	
}
