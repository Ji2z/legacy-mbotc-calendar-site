package com.ssafy.mbotc.sync;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig{
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTeamplate = new RestTemplate();
		return restTeamplate;
	}
	
}
