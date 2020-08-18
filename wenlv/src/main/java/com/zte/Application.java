package com.zte;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

import com.zte.common.config.RequestResponseLoggingInterceptor;

@EnableAutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@SpringBootApplication
@ComponentScan
@MapperScan("com.zte.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		
		RestTemplate restTemplate = new RestTemplate();
		 
		restTemplate.getInterceptors().add(new RequestResponseLoggingInterceptor());
		return restTemplate;
	}

}
