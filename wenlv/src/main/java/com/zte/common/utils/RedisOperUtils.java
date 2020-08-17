package com.zte.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisOperUtils {
	private static JedisCluster jedisCluster;
	private static Logger log = LoggerFactory.getLogger(RedisOperUtils.class);
	private static String[] ipAddressArray = null;
	private static String password = null;
	private static final int HOUR_SECONDS = 3600;
	private static final String NO_EXIST = "NX";
	private static final String EXIST = "XX";
	private static final String EXPIRE_SECONDS = "EX";

	
	public static final int HALF_HOUR_SECONDS = 1800;
	public static final int QUARTER_HOUR_SECONDS = 900;
	public static final int MINUTE_SECONDS = 60;
	public static final int DAY_SECONDS = MINUTE_SECONDS*24;


	/**
	 * 初始化redis集群连接池，由JedisCluster管理集群连接池
	 * 从数据库读取redis集群地址，然后初始化JedisCluster
	 */
	public static JedisCluster initialPool(String[] ips,String pw)
	{		
		if(ips!=null && ips.length>0){
			ipAddressArray = ips;
		}else{
			log.error("系统参数表中redis集群地址rsp.redis.url未正确配置");
		}
		password=pw;
	    String ip = "";
		int port;
		// 池基本配置
		if(!RedisOperUtils.isClusterAvaiable()){
			try{
				jedisCluster = null;
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(30);
				config.setMaxIdle(8);
				config.setMinIdle(5);
				config.setMaxWaitMillis(1000 * 10);
				config.setTestOnBorrow(false);
				config.setTestWhileIdle(true);
				//网络超时时间
				int timeout=12*1000;
				//读取超时时间
				int soTimeout=3000;
				//最大重试次数
				int maxAttempts=3;
		        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		        for(int i=0;i<ips.length;i++){
					ip = ips[i].substring(0, ips[i].indexOf(":"));
					port = Integer.parseInt(ips[i].substring(ips[i].indexOf(":") + 1,
							ips[i].length()));
					System.out.println("init jedis"+ip+"|"+port);
			        jedisClusterNodes.add(new HostAndPort(ip, port));
		        }
		        // 3个master 节点
		        // 3个master 节点	
		        if(StringUtils.isBlank(pw)){
		        	jedisCluster = new JedisCluster(jedisClusterNodes,timeout,config);
		        }else{
		        jedisCluster = new JedisCluster(jedisClusterNodes, timeout, soTimeout, maxAttempts, pw, config);
		        }
			}catch(Exception ex){
				log.error("init error "+ex.getMessage());
			}
		}
		return jedisCluster;
	}

	public static Map<String,String> getKVList(String key){
		Map<String, String> data = getJedisPool().hgetAll(key);
		return data;
	}
	
	/**
	 * 根据pattern查询key
	 */
	public static Set<String> keys(String pattern){  
		JedisCluster cluster = RedisOperUtils.getJedisPool();
		log.debug("Start getting keys...");  
        Set<String> keys = new HashSet<String>();  
        Map<String, JedisPool> clusterNodes = cluster.getClusterNodes();  
        for(String k : clusterNodes.keySet()){  
        	log.debug("Getting keys from: {}"+k);  
            JedisPool jp = clusterNodes.get(k);  
            Jedis connection = jp.getResource();  
            try {  
                keys.addAll(connection.keys(pattern));  
            } catch(Exception e){  
                log.error("Getting keys error: {}"+e);  
            } finally{  
                log.debug("Connection closed.");  
                connection.close();//用完一定要close这个链接！！！  
            }  
        }  
        log.debug("Keys gotten!");  
        return keys;  
    }


	public static boolean delkeys(String key) {
		Set<String> keys = keys(key+"*");
		if(keys==null||keys.isEmpty())return true;
		for(String k:keys) {
			getJedisPool().del(k);
		}
		return true;
	}


	/**
	 * @author yinsiwei
	 * @date 2020-07-17 17:15
	 * @param
	 * @return
	 * @throws
	 * @since
	 * 根据删除hash中的元素
	*/
	public static boolean hdelkeys(String key,String... filed){
		if (key==null||key.isEmpty()||filed==null||filed.length==0)return false;
		getJedisPool().hdel(key,filed);
		return true;
	}

	
	
	/**
	 * 根据key值前一段key值信息获取所有的对象列表
	 * 返回Map对象列表
	 * @param key
	 * @return
	 */
	public static List<Map<String,String>> getListMap(String key) {
		Set<String> keys = keys(key+"*");
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		if(keys==null||keys.isEmpty())return resultList;
		for(String k:keys) {
			Map<String,String> eo = getKVList(k);
			resultList.add(eo);
		}
		return resultList;
	}
	
	/**
	 * 根据key值前一段key值信息获取所有的对象列表
	 * 返回String对象列表
	 * @param key
	 * @return
	 */
	public static List<String> getListString(String key) {
		Set<String> keys = keys(key+"*");
		List<String> resultList = new ArrayList<String>();
		if(keys==null||keys.isEmpty())return resultList;
		for(String k:keys) {
			String eo = getJedisPool().get(k);
			resultList.add(eo);
		}
		return resultList;
	}
	
	private static boolean isClusterAvaiable() {
		if (jedisCluster == null) {
			return false;
		}
		Iterator<JedisPool> poolIterator = jedisCluster.getClusterNodes()
				.values().iterator();
		while (poolIterator.hasNext()) {
			JedisPool pool = poolIterator.next();
			if (pool.getNumActive() > 0) {
				return true;
			}
			try {
				pool.getResource();
				return true;
			} catch (Exception e) {
				log.error(e.getMessage());
				pool.destroy();
			}
		}
		return false;
	}

	public static void testHashOperate() {
		try {
			System.out.println("======================hash==========================");
			// 清空数据
			// System.out.println(jedis.flushDB());
			//String ipparam="192.168.100.67:7005";
			// String
			// ipparam="192.168.10.96:7000,192.168.10.96:7001,192.168.10.97:7002,192.168.10.97:7003,192.168.10.98:7004,192.168.10.98:7005";
//			 String ipparam="10.118.15.7:7000";
//			String ipparam = "192.168.10.98:7004";
			 
			String ipparam = "192.168.100.9:8000";
			String[] ips = ipparam.split(",");
			ipAddressArray = ips;
			try {
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(30);
				config.setMaxIdle(8);
				config.setMinIdle(5);
				config.setMaxWaitMillis(1000 * 10);
				config.setTestOnBorrow(false);
				config.setTestWhileIdle(true);
				int timeout = 12 * 1000;
				// 这东西 可以直接看到key 的分片数，就能知道放哪个 节点
				//System.out.println("getSlot=" + JedisClusterCRC16.getSlot("redis:dingbook:account"));
				Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
				for (int i = 0; i < ips.length; i++) {
					String ip = ips[i].substring(0, ips[i].indexOf(":"));
					int port = Integer.parseInt(ips[i].substring(
							ips[i].indexOf(":") + 1, ips[i].length()));
					System.out.println("init jedis" + ip + "|" + port);
					jedisClusterNodes.add(new HostAndPort(ip, port));
				}
				// 3个master 节点
				jedisCluster = new JedisCluster(jedisClusterNodes, timeout,
						config);
			} catch (Exception ex) {
				log.error("init error " + ex.getMessage());

			}
			Random random = new Random();
			String result="";
			for(int i=0;i<4;i++){
				result+=random.nextInt(10);
			}
//			System.out.println(result);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String date = sdf.format(new Date());
			jedisCluster.hset("hashs", "code", result);
//			jedis.hsetnx("hashs", RedisConstants.SEND_CODE_FIELD, result);
			System.out.println(jedisCluster.hvals("hashs"));
			jedisCluster.hset("hashs", "counter", "5");
			//jedisCluster.hset("hashs", RedisConstants.SEND_DATE_FIELD, date);
			System.out.println("获取hashs中所有的value：" + jedisCluster.hvals("hashs"));	
			jedisCluster.close();
			if(!RedisOperUtils.isClusterAvaiable()){
				jedisCluster = null;
				jedisCluster = RedisOperUtils.initialPool(ipAddressArray,password);
			}
			jedisCluster.hset("hashs", "code", "7777");
			System.out.println("获取hashs中所有的value：" + jedisCluster.hvals("hashs"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	public static JedisCluster getJedisPool() {
    	if( ! RedisOperUtils.isClusterAvaiable()){
    		RedisOperUtils.initialPool(ipAddressArray,password);
    	}
		return jedisCluster;
	}
	
	/**
	 * 根据key值获取String类型List
	 * @param key
	 * @return
	 */
	public static List<String> getList(String key) {
		return getJedisPool().hvals(key);
	}
	
	/**
	 * <p><b>Title:</b> exists
	 * <p><b>Description:</b>
	 * <p>判定某个key是否存在
	 * @param key
	 * @return
	 */
	public static Boolean exists(String key){
		return getJedisPool().exists(key);
	}
	
	
	
	
	
	/**
	 * 根据key值获取value
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return getJedisPool().get(key);
	}
	
	public static String set(String key,String value){
		return getJedisPool().set(key,value);
	}
	
	public static String hget(String key,String field){
		return getJedisPool().hget(key, field);
	}
	
	public static Long hset(String key,String field,String value){
		return getJedisPool().hset(key, field, value);
	}
	
	public static boolean hexists(String key,String field){
		return getJedisPool().hexists(key, field);
	}
	
	public static Long hincrBy(String key,String field,long value){
		return getJedisPool().hincrBy(key, field, value);
	}
	
	public static Long hincrBy(String key,int seconds){
		return getJedisPool().expire(key, seconds);
	}
	
	/**
	 * 按小时设置超时时间--一个小时
	 * 参数"NX"：key不存在时写入, "EX"按秒记时
	 * HOUR_SECONDS 小时时间
	 * key 键值
	 * value 值
	 * NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key 
	 *  EX|PX, expire time units: EX = seconds; PX = millisecond
	 * @return 
	 */
	public static String setLimitHour(String key,String value){
		return getJedisPool().set(key, value,NO_EXIST,EXPIRE_SECONDS,1*HOUR_SECONDS);
	}
	
	/**
	 * n:小时数量
	 */
	public static String setLimitHours(String key,String value,int n){
		return getJedisPool().set(key, value,NO_EXIST,EXPIRE_SECONDS,n*HOUR_SECONDS);
	}
	
	/**
	 * n:秒
	 */

	public static String setLimitSeconds(String key,String value,int n){
		String str = getJedisPool().get(key);
		String isExist;
		if(StringUtils.isEmpty(str)) {
			isExist = NO_EXIST;
		}else {
			isExist = EXIST;
		}
		return getJedisPool().set(key, value,isExist,EXPIRE_SECONDS,n);
	}
	
	/**
	 * 设置redis缓存有效时间到当天12点
	 * @param key
	 * @param value
	 * @return
	 */
	public static String setLimitTimeTo24(String key,String value) {
		int ttl = getTimeGapMills(0,24);
		return setLimitSeconds(key,value,ttl);
	}
	
	/**
	 * 设置redis缓存时间到第二天2点
	 * @param key
	 * @param value
	 * @return
	 */
	public static String setLimitTimeToNextDay2(String key,String value) {
		int ttl = getTimeGapMills(1,2);
		return setLimitSeconds(key,value,ttl);
	}
	
	/**
	 * 设置redis缓存时间到第二天hour点
	 * @param key
	 * @param value
	 * @param hour
	 * @return
	 */
	public static String setLimitTimeToNextDayTime(String key,String value,int hour) {
		int ttl = getTimeGapMills(1,hour);
		return setLimitSeconds(key,value,ttl);
	}
	
	/**
	 * 根据制定时间获取当前时间到指定时间之间的间隔
	 * @param day 当前日期后多少天
	 * @param hour 指定日期时间（<24）
	 * @return 当前时间到制定时间之间的秒
	 */
	private static int getTimeGapMills(int day,int hour) {
		Calendar cal = Calendar.getInstance();
		long curmills = cal.getTimeInMillis();
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		long endmills = cal.getTimeInMillis();
		return new BigDecimal(endmills - curmills).divide(new BigDecimal(1000),BigDecimal.ROUND_UP).intValue();
	}
	
	public static void main(String[] args) {
		// String ips="192.168.100.67:7005";
		// String[] ip=ips.split(",");
//		RedisOperUtils.testHashOperate();
		// RedisOperUtils redisOperUtils = new RedisOperUtils();
		// redisOperUtils.initialPool("10.118.15.7:6379","10.118.15.7:6379");
		// redisOperUtils.testHashOperate();



		//System.out.println(getTimeGapMills(0,21));
		
	}
	

}
