package com.zte.common.utils;

public class RedisKey
{
	//管理员信息保存 key可以token和appid分别保存一份
	public static final String ADMIN_INFO = "v:wenlv:admininfo:";
	//用户信息保存 key可以token和appid分别保存一份
	public static final String USER_INFO = "v:wenlv:userinfo:";

	//活动信息保存
	public static final String ACTIVITY_INFO = "v:wenlv:activityinfo:";
	
	//敏感词库
	public static final String SENSITIVE_WORDS  = "v:sensitive:words";
	
	//系统配置参数
	public static final String SYSTEM_CONFIG = "h:wenlv:sysconfigs";
}
