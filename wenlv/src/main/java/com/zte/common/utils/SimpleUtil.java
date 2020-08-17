package com.zte.common.utils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.alibaba.fastjson.JSONArray;


public class SimpleUtil
{	
	public static String limitCntName(String cntName) {
		return limitStr(cntName, 12);
	}
	
	public static String limitShortDesc(String shortDesc){
		return limitStr(shortDesc, 30);
	}
	
	public static String limitStr(String str, int length) {
		if (str != null && str.length() > length) {
			str = str.substring(0, length - 2)+"...";
		}
		return str;
	}
	


	
	/**
	 * 将list里的map对象key转换成小写，处理mybatis查询返回map时，key为大写
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List lowerListMapKey(List<Map<String, Object>> list) {
		List newList = new ArrayList();
		
		if(list != null && list.size() > 0){
		    for (int i = 0; i < list.size(); i++) {
	            newList.add(lowerMapKey(list.get(i)));
	        }
		}
		
		return newList;
	}
	
	public static List lowerListMapKey2(List<Map> list) {
		List newList = new ArrayList();
		
		if(list != null && list.size() > 0){
		    for (int i = 0; i < list.size(); i++) {
	            newList.add(lowerMapKey(list.get(i)));
	        }
		}
		
		return newList;
	}
	
	/**
	 * 把map里面的key转换为小写
	 * @param map
	 * @return
	 */
	public static Map<String,Object> lowerMapKey(Map<String ,Object> map){
		if(map == null){
			return map;
		}
		Map<String,Object> newMap = new HashMap<String,Object>();
		for (String key : map.keySet()) {
			if (!"ROWNO".equals(key)) {
				newMap.put(key.toLowerCase(), map.get(key));
			}
		}
		return newMap;
	}
	
	/**
	 * 将list里的map对象key转换成大写
	 * @param list
	 * @return
	 */
	public static List upperListMapKey(List<Map> list) {
		List newList = new ArrayList();
		
		if(list != null && list.size() > 0){
		    for (int i = 0; i < list.size(); i++) {
	            newList.add(upperMapKey(list.get(i)));
	        }
		}
		
		return newList;
	}
	
	/**
	 * 把map里面的key转换为大写
	 * @param map
	 * @return
	 */
	public static Map upperMapKey(Map<String ,Object> map){
		if(map == null){
			return map;
		}
		Map newMap = new HashMap();
		for (String key : map.keySet()) {
			newMap.put(key.toUpperCase(), map.get(key));
		}
		return newMap;
	}
	
	/**
	 * 获取当前时间到几天后的时间秒数
	 */
	public static int getSeconds(int day,int hour,int min) {
		Calendar cal=Calendar.getInstance();
		Date currentDate=new Date();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_MONTH,day);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
	    Integer seconds=(int)((cal.getTime().getTime()-currentDate.getTime())/1000);
	    return seconds;
	}
	
	
	/**
	 * 批量移除数组中的空对象
	 * @param jsonArray
	 * @return
	 */
	public static JSONArray bathRemoveNullArray(JSONArray jsonArray) {
		if(jsonArray.remove(null)) {
			bathRemoveNullArray(jsonArray);
		}
		return jsonArray;
	}

	/**
	 * 判断一个值是否为整数
	 *
	 * @param s
	 * @return
	 */
	public static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}

    /**
     * 生成8位随机数
     *
     * @return java.lang.String
     * @author zhongyong 2020/3/27 11:21
     */
    public static String getShortUuid() {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < 8; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if(i==0){
                charOrNum = "char";
            }
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        val = val.toUpperCase().replaceAll("O", "X");
        val = val.toUpperCase().replaceAll("0", "2");
        val = val.toUpperCase().replaceAll("1", "8");
        return val.toUpperCase().replaceAll("I", "9");
    }

    //生成min-max范围内随机整数
	public static Integer getRandomInt(Integer min,Integer max){
		return (min + (int)(Math.random()*(max-min+1)));
	}

/*	public static void main(String[] args) {
		*//*Admin admin = new Admin();
		admin.setAccount("test");
		admin.setPassword("123456");
		System.out.println(JSONObject.toJSONString(admin));*//*

		String sysconfig = PropertiesUtil.getStringByKey("usertoken.expire.time");
		System.out.println(sysconfig);
	}*/

}

