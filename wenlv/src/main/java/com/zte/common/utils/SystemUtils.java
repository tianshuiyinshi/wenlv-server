package com.zte.common.utils;


import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zte.bean.vo.AdminVo;
import com.zte.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

@Component
public class SystemUtils {
    private static Logger log = LoggerFactory.getLogger(SystemUtils.class);

    
	
	private static final String LOCK_SUCCESS = "OK";
	 private static final Long RELEASE_SUCCESS = 1L;
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;
    
    private static SystemUtils systemUtils;

    @Autowired
    SysConfigService sysConfigService;

    // 初始化静态参数
    @PostConstruct
    public void init() {
        systemUtils = this;
        systemUtils.sysConfigService=this.sysConfigService;
    }

    /**
            * 获取系统配置参数
     * @param cfgkey
     * @return
     */
    public static String getSysconfig(String cfgkey) {
    	return systemUtils.sysConfigService.selectCfgvalue(cfgkey);
    }
    
    
    /**
             *校验应用权限
     */
    public static boolean checAdminToken(HttpServletRequest request)
    {
    	String adminToken = request.getHeader("adminToken");
    	String tokenkey=RedisKey.ADMIN_INFO+adminToken;
    	if(!RedisOperUtils.exists(tokenkey)) {
    		return false;
    	}
    	int expiretime=Integer.valueOf(SystemUtils.getSysconfig("adminToken.expire.time"));
   		RedisOperUtils.getJedisPool().expire(tokenkey, expiretime);
   		return true;  
    }
    
    /**
     	 * 生成登录token 并保存管理员信息
     * @param param
     */
    public static String genAdminToken(JSONObject param) {
    	String token=AlgorithmUtils.genUserToken();
    	String tokenkey=RedisKey.ADMIN_INFO+token;
    	String userkey=RedisKey.ADMIN_INFO+param.getJSONObject("adminVo").getString("adminId");
    	JSONObject data=new JSONObject();
    	param.put("adminToken", token);
    	data.put("adminInfo", param);
    	RedisOperUtils.set(tokenkey, data.toString());
    	RedisOperUtils.set(userkey, data.toString());
    	int expiretime=Integer.valueOf(SystemUtils.getSysconfig("adminToken.expire.time"));
	    RedisOperUtils.getJedisPool().expire(tokenkey, expiretime);
	    RedisOperUtils.getJedisPool().expire(userkey, expiretime);
	    return token;
    }

  /**
     * 从缓存中获取管理员信息
     * @param request
     * @return
     */
    public static AdminVo getAdminInfo(HttpServletRequest request) {
    	String token = request.getHeader("adminToken");
    	if(StringUtils.isBlank(token)) {
    		return null;
    	}
    	String tokenkey=RedisKey.ADMIN_INFO+token;
    	if(RedisOperUtils.exists(tokenkey)) {
    		JSONObject data=JSONObject.parseObject(RedisOperUtils.get(tokenkey));
    		JSONObject adminInfo=data.getJSONObject("adminInfo");
    		AdminVo adminVo = adminInfo.getObject("adminVo",AdminVo.class);
    		log.info(adminVo.toString());
    		return adminVo;
    	}
    	return null;
    }


    /**
     * @author yinsiwei
     * @date 2020-07-21 21:06
	 * 注销缓存中管理员信息
    */
	public static void delAdminInfo(Integer adminId){
		String userkey=RedisKey.ADMIN_INFO+adminId;
		if(RedisOperUtils.exists(userkey)) {
    		JSONObject data=JSONObject.parseObject(RedisOperUtils.get(userkey));
    		String adminToken = data.getJSONObject("adminInfo").getString("adminToken");		
    		String tokenkey=RedisKey.ADMIN_INFO+adminToken;
    		RedisOperUtils.delkeys(tokenkey);
    		RedisOperUtils.delkeys(userkey);
    	}
	}
	
	
    
    /**
     * @param phone 字符串类型的手机号
     * 传入手机号,判断后返回
     * true为手机号,false相反
     * */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    
    
    /**
     * 获取ip地址
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
	    /**
		     * 阻塞式锁
		*
		*@author songyong
		*@param  requestid  唯一标示符，在释放锁时，用于判断是否锁已经被其他进程获取了，错误被释放，推荐使用UUID.randomUUID().toString()生成
		* @param lockKey    redis key至
		* @param expireTime 过期时间
		* @return
		*/
		public static boolean blockLock(String lockKey,String requestid,int expireTime) {		
			String result = RedisOperUtils.getJedisPool().set(lockKey, requestid, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			 
		   if (LOCK_SUCCESS.equals(result)) {
		       return true;
		   }else {
		   	Random random = new Random();
		   	// 0-99
		   	int num = random.nextInt(DEFAULT_ACQUIRY_RESOLUTION_MILLIS)+1;
				try {
					Thread.sleep(num);
					blockLock(lockKey,requestid, expireTime);
				} catch (InterruptedException e) {
					log.error("锁等待异常："+e);
				}
				
		   }
		   return false;
		}
	
		/**
		   *非阻塞式锁
		*
		*@author songyong
		*@param  requestid  唯一标示符，在释放锁时，用于判断是否锁已经被其他进程获取了，错误被释放，推荐使用UUID.randomUUID().toString()生成
		* @param lockKey    redis key至
		* @param expireTime 过期时间
		* @return
		*/
		public static boolean lock(String lockKey,String requestid,int expireTime) {		
			String result =  RedisOperUtils.getJedisPool().set(lockKey, requestid, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);		 
		  if (LOCK_SUCCESS.equals(result)) {
		      return true;
		  }
		  return false;
		}
		
		/**
		* 释放锁
		* @author songyong
		* @param lockKey
		* @param requestId
		* @return
		*/
		public static boolean unlock(String lockKey, String requestId) {
			String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		   Object result =  RedisOperUtils.getJedisPool().eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
		
		   if (RELEASE_SUCCESS.equals(result)) {
		       return true;
		   }
		   return false;
		
		}




}
