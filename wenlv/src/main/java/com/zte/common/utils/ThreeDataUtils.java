package com.zte.common.utils;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 第三方数据获取工具类
 * 
 * @author Administrator
 *
 */
@Component
public class ThreeDataUtils {
	private static Logger log = LoggerFactory.getLogger(ThreeDataUtils.class);

	private static ThreeDataUtils threeDataUtils;

	@Autowired
	RestTemplate restTemplate;

	// 初始化静态参数
	@PostConstruct
	public void init() {
		threeDataUtils = this;
		threeDataUtils.restTemplate = this.restTemplate;
	}

//	/**
//	 * 主平台微服务接口应用鉴权
//	 *
//	 * @return
//	 */
//	public static String oauthClientKey() {
//		StringBuffer url = new StringBuffer();
//		url.append(SystemUtils.getSysconfig("micro.interface.ip") + "/oauth/client/key");
//		url.append("?clientSource=" + SystemUtils.getSysconfig("micro.client.source"));
//		url.append("&clientId=" + SystemUtils.getSysconfig("micro.client.id"));
//		url.append("&clientSecret=" + SystemUtils.getSysconfig("micro.client.secret"));
//		JSONObject result = threeDataUtils.restTemplate.getForEntity(url.toString(), JSONObject.class).getBody();
//		return Optional.ofNullable(result)
//				.filter(a -> "0000".equals(a.getString("code")) && "success".equals(a.getString("message")))
//				.map(a -> a.getString("key_type") + " " + a.getString("key")).orElse("");
//	}

//	/**
//	 * 发送短信
//	 *
//	 * @param content
//	 * @param phone
//	 * @return
//	 */
//	public static boolean mainRestSendPhoneMsg(String content, String phone) {
//		StringBuffer url = new StringBuffer();
//		url.append(SystemUtils.getSysconfig("micro.interface.ip"))
//				.append("/cappay/rest/order/package/sendmsg/")
//				.append(phone);
//
//		HttpHeaders header = new HttpHeaders();
//		header.add("AuthorizationClient", oauthClientKey());
//		Map<String, String> requestBody = new HashMap<>();
//		requestBody.put("userlabel", phone);
//		requestBody.put("msg", content);
//		HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, header);
//		JSONObject result = threeDataUtils.restTemplate.postForEntity(url.toString(), requestEntity, JSONObject.class)
//				.getBody();
//		if (result == null) {
//			log.error("获取主平台验证码接口网络异常");
//			return false;
//		}
//		if ("9999".equals(result.getString("code"))) {
//			log.error("获取主平台验证码接口异常：" + result.getString("message"));
//			return false;
//		}
//		return true;
//	}

//	/**
//	 * 发送验证码
//	 *
//	 * @param phone
//	 * @return
//	 */
//	public static Boolean sendCode(String phone) {
//		StringBuffer url = new StringBuffer();
//		url.append(SystemUtils.getSysconfig("micro.interface.ip") + "/mainrest/rest/read/user/getcode/9");
//		HttpHeaders header = new HttpHeaders();
//		header.add("AuthorizationClient", oauthClientKey());
//		Map<String, String> requestBody = new HashMap<String, String>();
//		requestBody.put("userlabel", phone.toString());
//		requestBody.put("codetype", "3");
//		requestBody.put("useridtype", "1");
//		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, header);
//
//		JSONObject result = threeDataUtils.restTemplate.postForEntity(url.toString(), requestEntity, JSONObject.class)
//				.getBody();
//		if (result == null) {
//			log.error("获取主平台验证码接口网络异常");
//			return false;
//		}
//		if ("9999".equals(result.getString("code"))) {
//			log.error("获取主平台验证码接口异常：" + result.getString("message"));
//			return false;
//		}
//		log.info("短信验证码："+result);
//		return true;
//	}

//	/**
//	 * 校验验证码
//	 *
//	 * @param phone
//	 * @param code
//	 * @return
//	 */
//	public static Boolean checkCode(String phone, String code) {
//		StringBuffer url = new StringBuffer();
//		url.append(SystemUtils.getSysconfig("micro.interface.ip") + "/mainrest/rest/read/user/checkcode/9");
//		HttpHeaders header = new HttpHeaders();
//		header.add("AuthorizationClient", oauthClientKey());
//		Map<String, String> requestBody =new HashMap<String, String>();
//		requestBody.put("userlabel", phone);
//		requestBody.put("codetype", "5");
//		requestBody.put("validatecode", code);
//		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, header);
//
//		JsonResult result = threeDataUtils.restTemplate.postForEntity(url.toString(), requestEntity, JsonResult.class)
//				.getBody();
//		if (result == null) {
//			log.error("校验短信验证码网络异常");
//			return false;
//		}
//		if ("0000".equals(result.getCode())) {
//			return true;
//		} else {
//			log.info(result.getMessage().toString());
//			return false;
//		}
//
//	}

//	/**
//	 * 获取主平台书籍详情
//	 */
//	public static JSONObject cntdetail(String cntidx) {
//		StringBuffer url = new StringBuffer();
//		url.append(SystemUtils.getSysconfig("rsp.domain.wapurl") + "/rest/read/cnt/cntdetail/9/" + cntidx + "/0");
//
//		JSONObject result = threeDataUtils.restTemplate.getForEntity(url.toString(), JSONObject.class).getBody();
//		if (result == null) {
//			log.error("获取主平台资源详情网络异常" + cntidx);
//			return null;
//		}
//		if ("9999".equals(result.getString("code"))) {
//			log.error("获取主平台资源" + result.getString("message"));
//			return null;
//		}
//		return result.getJSONObject("message");
//	}

//	/**
//	 * 获取主平台章节列表
//	 */
//	public static JSONArray cntChalist(String cntidx, String page, String limit, String sorttype) {
//
//		StringBuffer url = new StringBuffer();
//		url.append(SystemUtils.getSysconfig("rsp.domain.wapurl") + "/rest/read/cnt/chalist/9/");
//		url.append(cntidx).append("/" + page).append("/" + limit).append("/" + sorttype);
//
//		JSONObject result = threeDataUtils.restTemplate.getForEntity(url.toString(), JSONObject.class).getBody();
//		if (result == null) {
//			log.error("获取主平台资源目录列表网络异常" + cntidx);
//			return null;
//		}
//		if ("9999".equals(result.getString("code"))) {
//			log.error("获取主平台资源目录" + result.getString("message"));
//			return null;
//		}
//		return result.getJSONArray("message");
//	}
//
//	/**
//	  * 获取主平台章节内容
//	  */
//	 public static Object wordsdetail(String cntidx,String chapterallindex,String cnttype) {
//		 StringBuffer url = new StringBuffer();
//		 url.append(SystemUtils.getSysconfig("rsp.domain.wapurl")+"/rest/read/cnt/encryptwordsdetail/9?");
//		 url.append("cntindex="+cntidx+"&");
//		 url.append("chapterallindex="+chapterallindex+"&");
//		 url.append("cnttypeflag="+cnttype+"&");
//		 url.append("charpterflag=1");
//		 JSONObject result = threeDataUtils.restTemplate.getForEntity(url.toString(), JSONObject.class).getBody();
//		 if (result == null) {
//			log.error("获取主平台章节内容网络异常" + cntidx+"-"+chapterallindex);
//			return null;
//		 }
//		 if ("9999".equals(result.getString("code"))) {
//			log.error("获取主平台章节内容网络异常" + result.getString("message"));
//			return null;
//		 }
//		 String message = result.getString("message");
//
//		 //解密
//		 try {
//			message = AESUtil.decrypt(result.getString("message"), AESKey.CHAPTER_ENCRYPT_AES_KEY);
//			if(result.containsKey("isEpub")&&"1".equals(result.getString("isEpub"))) {
//				return JSONObject.parseObject(message);
//			}
//			 return message;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("AES解密失败" + e);
//			return null;
//		}
//
//	 }
//
//    /**
//     * 获取视频章节列表
//     *
//     * @return
//     */
//    public static JSONObject getVideoChapters(String ptcontentid, String page, String limit) {
//        StringBuffer url = new StringBuffer();
//        url.append(SystemUtils.getSysconfig("xinyewu.interface.url")).append("/rest/B1/9/V1/listVideoChapterList?");
//        url.append("cntidx=").append(ptcontentid).append("&pagenum=").append(page).append("&pagesize=").append(limit);
//        return threeDataUtils.restTemplate.getForEntity(url.toString(), JSONObject.class).getBody();
//    }
//
//    /**
//     * 获取视频播放地址
//     *
//     * @return
//     */
//    public static JSONObject getVideoChapterUri(String ptcontentid, String chapteridx) {
//        StringBuffer url = new StringBuffer();
//        url.append(SystemUtils.getSysconfig("xinyewu.interface.url")).append("/rest/B1/9/V1/getVideoChapterDetail?");
//        url.append("cntidx=").append(ptcontentid).append("&chapteridx=").append(chapteridx);
//        return threeDataUtils.restTemplate.getForEntity(url.toString(), JSONObject.class).getBody();
//    }
//
//    /**
//     * 获取音频章节列表
//     *
//     * @return
//     */
//    public static JSONObject getAudioChapters(String ptcontentid, String page, String limit) {
//        StringBuffer url = new StringBuffer();
//        url.append(SystemUtils.getSysconfig("xinyewu.interface.url")).append("/rest/A1/V1/9/getContentChapters?");
//        url.append("cntindex=").append(ptcontentid).append("&pageno=").append(page).append("&pagesize=").append(limit);
//        return threeDataUtils.restTemplate.getForEntity(url.toString(), JSONObject.class).getBody();
//    }
//
//    /**
//     * 获取音频播放地址
//     *
//     * @return
//     */
//    public static JSONObject getAudioChapterUri(String chapterIdx) {
//        StringBuffer url = new StringBuffer();
//        url.append(SystemUtils.getSysconfig("xinyewu.interface.url")).append("/rest/A1/V1/9/getChapterUri?");
//        url.append("chapteridx=").append(chapterIdx);
//        return threeDataUtils.restTemplate.getForEntity(url.toString(), JSONObject.class).getBody();
//    }
}
