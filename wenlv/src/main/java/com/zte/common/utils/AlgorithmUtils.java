package com.zte.common.utils;


import java.util.UUID;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class AlgorithmUtils {
	// private static Logger log = LoggerFactory.getLogger(SystemUtils.class);
	 
	/**
	 * 应用token算法
	 * @return
	 */
	 public static String genAccessToken(String sign) {
		 //生成规则签名+时间纳秒转md5 aes加密后截取前32位
		 String ss=sign+System.nanoTime();
		 String md5ss=DigestUtils.md5DigestAsHex(ss.getBytes());
		 String accesstoken=AESUtil.encryptAES(md5ss, AESKey.APP_TOKEN_KEY).substring(0, 32);
		 return accesstoken;
	 }
	 
	 /**
	  * 登录token算法
	 */
	 public static String genUserToken() {
		 //生成规则签名+时间纳秒md5 aes加密后截取前32位
		 String ss=UUID.randomUUID().toString()+System.nanoTime();
		 String md5ss=DigestUtils.md5DigestAsHex(ss.getBytes());
		 String accesstoken=AESUtil.encryptAES(md5ss, AESKey.APP_TOKEN_KEY).substring(0, 32);
		 return accesstoken;
	 }
	 
	 /**
	  * 登录token特征值生成
	  */
	 public static String genEigenvalue() {
		 String s = UUID.randomUUID().toString();
		 s = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
		 return s.substring(0, 12);
	 }


	 
	 public static void main(String[] args) {
		 for (int i = 0; i < 105; i++) {
			 System.out.println(genEigenvalue());
			 }
	}
}
