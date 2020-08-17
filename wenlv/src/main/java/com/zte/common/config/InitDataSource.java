package com.zte.common.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



/**
* <p>Title: 预加载数据库链接<／p>
* @author songyong
* @date 2018年1月2日
* 
*/
@Component
@Order(value=0)
public class InitDataSource implements CommandLineRunner{
	private static Logger log =  LoggerFactory.getLogger(InitDataSource.class);
	
	
	@Autowired
	ApplicationContext applicationContext;
	public void run(String... arg0) throws Exception {
		  // 获取配置的数据源
        DataSource dataSource = applicationContext.getBean(DataSource.class);
		log.info("正在连接数据库。。。");
		try {
			dataSource.getConnection();
			log.info("连接数据库完成");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("数据库连接异常", e);
		}
	}

}
