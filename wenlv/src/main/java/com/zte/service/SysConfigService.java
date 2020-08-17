package com.zte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.bean.SysConfig;
import com.zte.common.utils.RedisKey;
import com.zte.common.utils.RedisOperUtils;
import com.zte.dao.SysConfigMapper;

@Service
public class SysConfigService {
	
	@Autowired
	SysConfigMapper sysConfigMapper;
	
	public List<SysConfig> selectAll() {
		return sysConfigMapper.selectAll();
	}
	
	public String selectCfgvalue(String cfgkey) {
		//先查缓存再走数据库
		String rediskey=RedisKey.SYSTEM_CONFIG;
		//RedisOperUtils.hdelkeys(rediskey,cfgkey);
		if(RedisOperUtils.hexists(rediskey, cfgkey)) {
			return RedisOperUtils.hget(rediskey, cfgkey);
		}		
		SysConfig sysConfig = sysConfigMapper.selectByCfgkey(cfgkey);	
		if(sysConfig!=null&&sysConfig.getConfigvalue()!=null) {
			RedisOperUtils.hset(rediskey, cfgkey,sysConfig.getConfigvalue());
		}
		return sysConfig.getConfigvalue();
	}
	
	public String PUSHCahe(String cfgkey) {
		String rediskey=RedisKey.SYSTEM_CONFIG;		
		SysConfig sysConfig = sysConfigMapper.selectByCfgkey(cfgkey);	
		if(sysConfig!=null&&sysConfig.getConfigvalue()!=null) {
			RedisOperUtils.hset(rediskey, cfgkey,sysConfig.getConfigkey());
		}
		return sysConfig.getConfigvalue();
	}

	/**
	 * @author yinsiwei
	 * @date 2020-08-10 11:01
	 * 配置信息调整后刷新缓存
	*/
	public void flashCfgvalue(String cfgkey){
		String rediskey=RedisKey.SYSTEM_CONFIG;
		RedisOperUtils.hdelkeys(rediskey,cfgkey);

		SysConfig sysConfig = sysConfigMapper.selectByCfgkey(cfgkey);
		if(sysConfig!=null&&sysConfig.getConfigvalue()!=null) {
			RedisOperUtils.hset(rediskey, cfgkey,sysConfig.getConfigvalue());
		}
	}
}
