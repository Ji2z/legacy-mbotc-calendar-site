package com.ssafy.mbotc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
	LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
	return lettuceConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	redisTemplate.setConnectionFactory(redisConnectionFactory());
	redisTemplate.setKeySerializer(new StringRedisSerializer());
	redisTemplate.setValueSerializer(new StringRedisSerializer());
	return redisTemplate;
	}
	
	@Bean
	public StringRedisTemplate stringRedisTemplate() {
	StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
	stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
	stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
	stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
	return stringRedisTemplate;
	}

	// 참고자료 1 : https://oingdaddy.tistory.com/310
	// 참고자료 2 : https://ssoco.tistory.com/19
	// 참고자료 3 : https://sabarada.tistory.com/105
}
