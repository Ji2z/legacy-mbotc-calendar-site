package com.ssafy.mbotc.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mbotc.entity.Notice;
import com.ssafy.mbotc.entity.User;

@Repository
public interface NoticeRepository extends JpaRepository<User, Long> {
	
	@Transactional
	@Query("select * from notice where extract(year from time)= :year and extract(month from time)= :month and channel_id := channelId")
	Optional<List<Notice>> findByYearAndMonth(@Param("year") String year, @Param("month") String month, @Param("channelId") String channelId);
	
}
