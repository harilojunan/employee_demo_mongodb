package com.aeturnum.employee.demo.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.aeturnum.employee.demo.util.AppConstants;

@Configuration
public class RedisConfig {
	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
			   .entryTtl(Duration.ofMinutes(60)).disableCachingNullValues()
			   .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}
	
	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
	    return builder -> builder
	                      .withCacheConfiguration(AppConstants.HASH_VALUE,
	                       RedisCacheConfiguration.defaultCacheConfig()
	                      .entryTtl(Duration.ofMinutes(10)));
	}

}
