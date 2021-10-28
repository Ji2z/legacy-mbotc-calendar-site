package com.ssafy.mbotc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mbotc.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
	
	@Transactional
	@Query(value = "select * from notice where (now() between start_time and end_time or (extract(year from time)=:year and extract(month from time)=:month)) and channel_id = (select id from channel where token = :channelId)", nativeQuery = true)
	List<Notice> findAllByYearAndMonth(@Param("year") int year, @Param("month") int month, @Param("channelId") long channelId);
	
	@Transactional
	Notice findByToken(String token);
	
	@Transactional
	@Query(value = "select * from notice where (now() between start_time and end_time or (extract(year from time)=2021 and extract(month from time)=10 and extract(day from time)=28)) and channel_id = (select id from channel where token = :channelId)", nativeQuery = true)
	List<Notice> findAllByYearAndMonthAndDay(@Param("year") int year, @Param("month") int month, @Param("day") int day, @Param("channelId") long channelId);
}
