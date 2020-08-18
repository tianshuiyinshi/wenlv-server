package com.zte.dao;

import com.alibaba.fastjson.JSONObject;
import com.zte.bean.Admin;
import com.zte.bean.Menu;
import com.zte.bean.ResourceActivity;
import com.zte.bean.Role;
import com.zte.bean.dto.AuditDto;
import com.zte.bean.vo.*;
import com.zte.common.utils.*;
import com.zte.service.RoleMenuService;
import com.zte.service.RoleService;
import com.zte.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static com.zte.common.utils.FtpUtils.*;
import static com.zte.common.utils.RedisOperUtils.getJedisPool;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

/*
 * @author yinsiwei
 * @date 2020-07-27 15:24
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SysConfigService sysConfigService;


    @Test
    public void queryMenusByRoleTest() {
        Integer roleId = 1;
        Set<Menu> menus = roleMenuService.queryMenusByRole(roleId);
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }

    @Test
    public void findSingleRoleTest() {
        JsonResult result;
        Integer roleId = 1;
        HashMap resultObject = new HashMap<>();
        Role role = roleService.queryRoleById(roleId);
        Set<Menu> menus = roleMenuService.queryMenusByRole(roleId);
        Role role1 = roleService.queryRoleById(roleId);
        Set<Menu> menus1 = roleMenuService.queryMenusByRole(roleId);
        resultObject.put("role", role);
        resultObject.put("menus", menus);
        resultObject.put("role", role1);
        resultObject.put("menus", menus1);
        result = JsonResult.getSuccess(resultObject);
        System.out.println(result.toString());
    }

    @Test
    public void selectCfgvalue() {
        String cfgkey = "adminToken.expire.time";
        String s = sysConfigService.selectCfgvalue(cfgkey);
        System.out.println(s);
    }

    @Test
    public void FtpUtilsTest() throws FileNotFoundException {

        LocalDateTime now = LocalDateTime.now();
        Date date = new Date();
        Date bespeakDate=null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,3);
        Date time = calendar.getTime();

        try {
            bespeakDate = new SimpleDateFormat("yyyyMMdd").parse("20200815");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println(Calendar.getInstance().get(Calendar.SECOND));
        System.out.println(date.getSeconds());
        System.out.println(now.getSecond());
        System.out.println(DateUtil.getRemainSecondsOneDay(bespeakDate));
        System.out.println(DateUtil.getRemainSecondsManyDay(new Date(),bespeakDate));


//      sysConfigService.flashCfgvalue("FTP.BASEPATH");
//      File file = new File("C:\\Users\\Administrator\\Desktop\\专利\\de5128173209c40de42d0eb12bc57ad4.jpg");
//      InputStream fileInputStream = new FileInputStream(file);
//      InputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//      String fileName = file.getName();

//      String result = uploadFile(DateUtil.getDBDatetime().substring(0,8), "ysw"+fileName, bufferedInputStream);
//      if(result.isEmpty()) {
//          System.out.println("true");
//      }else {
//          System.out.println("false");
//      }
//      Admin admin = new Admin();
//      admin.setAdminId(1);
//      ArrayList<Integer> roleIds = new ArrayList<>();
//      roleIds.add(1);
//      JSONObject jsonObject = new JSONObject();

//      jsonObject.put("admin", admin);
//      jsonObject.put("roleIds", roleIds);
//      System.out.println(jsonObject.toJSONString());

//      RoleVo roleVo = new RoleVo();
//      roleVo.setId(999);

//      Menu menu1 = new Menu();
//      Menu menu2 = new Menu();
//      Menu menu3 = new Menu();
//      menu1.setId(1);
//      menu2.setId(2);
//      menu3.setId(3);


//      showPathTest("/home/ftp_test/static/video/2020/08/04");
        //deleteFile("/home/ftp_test/static/video/20200804","9eba03fbc2fb2696857093721612d0f7.mp4");

    }

    @Test
    public void RedisTest1() {
        String activityKey = RedisKey.ACTIVITY_INFO + "activityid1" + "bespeakday20201001";
        Integer j = 0;
        String activityToken = "activityToken";

/*        while (getJedisPool().llen(activityKey)!=0){
            String lpop = getJedisPool().lpop(activityKey);
            System.out.println(lpop);
        }*/


        System.out.println("开始创建活动令牌桶" + DateUtil.toLocalDateTime(new Date()));
        for (Integer i = 0; i < 2000; i++) {
            getJedisPool().lpush(activityKey,activityToken);
        }
        System.out.println("活动令牌桶创建完成" + DateUtil.toLocalDateTime(new Date()));
        System.out.println("令牌是否存在： "+getJedisPool().exists(activityKey));
        System.out.println(getJedisPool().llen(activityKey));


        while (getJedisPool().llen(activityKey) != 0) {
//          Integer randomInt = SimpleUtil.getRandomInt(1, 8);
            Long lrem = getJedisPool().lrem(activityKey, getJedisPool().llen(activityKey), activityToken);
            System.out.println(DateUtil.toLocalDateTime(new Date()) + " " + lrem);
            System.out.println("剩余令牌数量： "+getJedisPool().llen(activityKey));


//        getJedisPool().lpop(activityKey);
//          j++;
//          if (j==8){
//              System.out.println(DateUtil.toLocalDateTime(new Date()));
//              j=0;
//          }
        }
        System.out.println(getJedisPool().llen(activityKey));
    }

    @Test
    public void redisTest2(){
        String activityKey = RedisKey.ACTIVITY_INFO + "activityid1" + "bespeakday20201001";
        String activityToken = "activityToken";

        System.out.println("开始创建活动令牌桶" + DateUtil.toLocalDateTime(new Date()));
        for (Integer i = 0; i < 5; i++) {
            getJedisPool().lpush(activityKey,activityToken);
        }
        getJedisPool().expire(activityKey,5);
        getJedisPool().expire(activityKey+"test",5);


        for (Integer i = 0; i < 5; i++) {
            getJedisPool().lpush(activityKey+"test",activityToken);
        }

        getJedisPool().getClusterNodes().values().stream().forEach(pool -> {
            boolean done = false;
            String cur = SCAN_POINTER_START;
            try (Jedis jedisNode = pool.getResource()) {
                System.out.println("******************键中包含 activityid1 的键**********************");
                while (!done) {
                    ScanResult<String> resp = jedisNode.scan(cur, new ScanParams().match("activityid1").count(Integer.MAX_VALUE));


                    for (String s : resp.getResult()) {
                        System.out.println(s);
                    }
                    cur = resp.getStringCursor();
                    if (cur.equals(SCAN_POINTER_START)) {
                        done = true;
                    }
                }
                System.out.println("**************************************************************");
            }
        });

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getJedisPool().getClusterNodes().values().stream().forEach(pool -> {
            boolean done = false;
            String cur = SCAN_POINTER_START;
            try (Jedis jedisNode = pool.getResource()) {
                System.out.println("******************键中包含 activityid1 的键**********************");
                while (!done) {
                    ScanResult<String> resp = jedisNode.scan(cur, new ScanParams().match("activityid1").count(Integer.MAX_VALUE));
                    for (String s : resp.getResult()) {
                        System.out.println(s);
                    }
                    cur = resp.getStringCursor();
                    if (cur.equals(SCAN_POINTER_START)) {
                        done = true;
                    }
                }
                System.out.println("**************************************************************");
            }
        });
        System.out.println(getJedisPool().llen(activityKey));
    }

    @Test
    public void flashCfgValueTest() {
        sysConfigService.flashCfgvalue("FTP.BASEPATH");
    }

    @Test
    public void getJSONObject(){

//      ResourceActivity resourceActivity1 = new ResourceActivity();
//      ResourceActivity resourceActivity2 = new ResourceActivity();
//      resourceActivity1.setUpdatetime("20201001000000");
//      resourceActivity2.setUpdatetime("20201002000000");
//      ArrayList<Object> objects = new ArrayList<>();
//      objects.add(resourceActivity1);
//      objects.add(resourceActivity2);
//      System.out.println(JSONObject.toJSONString(objects));

//      AuditDto auditDto = new AuditDto();
//      auditDto.setStatus(2);
//      ArrayList<Integer> integers = new ArrayList<>();
//      integers.add(1);
//      integers.add(2);

//      auditDto.setResourceIds(integers);
//      System.out.println(JSONObject.toJSONString(auditDto));


//      ResourceNewsVo resourceNewsVo = new ResourceNewsVo();
//      ResourceLabelVo resourceLabelVo = new ResourceLabelVo();
//      ResourceLabelVo resourceLabelVo1 = new ResourceLabelVo();
//      resourceLabelVo.setTableid(1);
//      resourceLabelVo.setLabelid(1);
//      resourceLabelVo.setResourceid(1);
//      resourceLabelVo1.setTableid(1);
//      resourceLabelVo1.setLabelid(2);
//      resourceLabelVo1.setResourceid(1);
//      List<ResourceLabelVo> objects = new ArrayList<>();
//      objects.add(resourceLabelVo);
//      objects.add(resourceLabelVo1);
//      resourceNewsVo.setResourceLabelVos(objects);
//      System.out.println(JSONObject.toJSONString(resourceNewsVo));


        ResourceActivityVo resourceActivityVo1 = new ResourceActivityVo();
        resourceActivityVo1.setResourcetitle("1");
        ResourceActivityVo resourceActivityVo2 = new ResourceActivityVo();
        resourceActivityVo2.setResourcetitle("2");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("record",resourceActivityVo1);
        jsonObject.put("record",resourceActivityVo2);

        System.out.println(jsonObject.toJSONString());

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        System.out.println(JSONObject.toJSONString(integers));



    }
}
