package com.ssafy.mbotc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
//
//	@Override
//	public String uploadFileToMM(MultipartFile file, String mattermostUrl, String token, String channelId) {
//		String GET_TEAM_LIST = mattermostUrl + "/api/v4/files";
//		StringBuilder response = new StringBuilder();
//		
//		try {
//            URL url = new URL(GET_TEAM_LIST);
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Authorization", "bearer "+token);
//            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=^-----^");
//            conn.setDoOutput(true);
//            
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("channel_id", channelId);
//            jsonObject.put("filename", file);
//            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
//            osw.write(jsonObject.toString());
//            osw.flush();
//            osw.close();
//            
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
//            if (conn.getResponseCode() != 200) {
//                System.out.println("Failed: HTTP error code : " + conn.getResponseCode());
//            	throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
//            } 
//            
//            String line = null;
//            while((line = br.readLine()) != null){
//                response.append(line);
//            }
//            br.close();           
//            conn.disconnect();
//        } catch (IOException e) {
//            System.out.println("RestCall Fail : " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//		
//		System.out.println(response.toString());
//
//		return null;
//	}

}
