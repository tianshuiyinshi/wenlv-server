package com.zte.common.utils;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 授权相关
 * @author Administrator
 *
 */
@Component
public class AuthUtils {
    private static Logger log = LoggerFactory.getLogger(AuthUtils.class);
    
    private static AuthUtils authUtils;

    // 初始化静态参数
    @PostConstruct
    public void init() {
    	authUtils = this;
    }

     
    /**
     *校验应用权限
     */
/*    public static boolean checApp(HttpServletRequest request)
    {
    	String accesstoken = request.getHeader("accesstoken");
    	String tokenkey=RedisKey.APP_INFO+accesstoken;
    	if(!RedisOperUtils.exists(tokenkey)) {
    		return false;
    	}
    	
    	JSONObject data =JSONObject.parseObject(RedisOperUtils.get(tokenkey));
    	String appkey=RedisKey.APP_INFO+data.getString("appid");
    	//设置过期时间7天，等建表后由数据库决定
    	int expiretime=Integer.valueOf(SystemUtils.getSysconfig("app.expire.time"));
   		RedisOperUtils.getJedisPool().expire(appkey, expiretime);
   		RedisOperUtils.getJedisPool().expire(RedisKey.APP_INFO+accesstoken, expiretime);
   		return true;  
    }
    */
    
	/**
	 * 从请求头中获取应用token
	 */
    public static String getToken(HttpServletRequest request) {
    	return  request.getHeader("accesstoken");
    }

}
