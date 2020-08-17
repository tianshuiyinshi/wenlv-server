package com.zte.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.zte.common.utils.RedisOperUtils;

import java.util.Arrays;

@Component
@Order(value=1)
public class InitRedis implements CommandLineRunner{
	private static Logger log =  LoggerFactory.getLogger(InitRedis.class);

	@Value("${redis_host}")
	private String redisHost;
	
	@Value("${redis_password}")
	private String pw;
	
	@Override
    public void run(String... arg0) throws Exception {
		//初始化redis
     	if(!StringUtils.isEmpty(redisHost))
     	{
     		RedisOperUtils.initialPool(redisHost.split(","),pw);
     		log.info("redis连接成功");
     	}
	}
}
