package com.zte.rest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.bean.vo.ResourceActivityVo;
import com.zte.bean.vo.ResourceBespeakVo;
import com.zte.bean.vo.ResourceSiteVo;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.RedisKey;
import com.zte.service.ResourceActivityService;
import com.zte.service.ResourceBespeakService;
import com.zte.service.ResourceSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.zte.common.utils.RedisOperUtils.getJedisPool;

/**
 * @author yinsiwei
 * @date 2020-07-31 09:43
 */
@Api(tags = "预约操作类")
@RestController
@RequestMapping("/resourceBespeak")
public class BespeakRest {

    @Autowired
    ResourceBespeakService resourceBespeakService;

    @Autowired
    ResourceActivityService resourceActivityService;

    @Autowired
    ResourceSiteService resourceSiteService;

    @Autowired
    HttpServletRequest req;

    @ApiOperation("新增预约接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceBespeakVo",
                    value ="参数实例：{\"startTime\":\"20200731150000\",\"endTime\":\"20200731160001\"}",
                    paramType="body",
                    dataType = "ResourceBespeakVo",
                    required=true )
    })
    @PostMapping("/addBespeak")
    public JsonResult addBespeak(@RequestBody ResourceBespeakVo resourceBespeakVo){
        JsonResult result;
        if (resourceBespeakVo!=null&&resourceBespeakVo.getBespeakamount()!=null){
            String activityKey = RedisKey.ACTIVITY_INFO + resourceBespeakVo.getTableid() + resourceBespeakVo.getResourceid() + resourceBespeakVo.getBespeakday();
            String activityToken = "activityToken";
            JedisCluster jedisPool = getJedisPool();
            Integer bespeakamount = resourceBespeakVo.getBespeakamount();
            Integer maxamount=0;
            Date bespeakDate=null;
            //如果该用户为该日期活动首次预约，则往redis中添加该资源令牌池，数量为最大可报名数
            if(!jedisPool.exists(activityKey)){
                ResourceActivityVo resourceActivityVo = resourceBespeakVo.getResourceActivityVo();
                ResourceSiteVo resourceSiteVo = resourceBespeakVo.getResourceSiteVo();
                if (resourceBespeakVo.getTableid()==1){
                    maxamount = resourceActivityVo.getMaxamount();
                }
                if (resourceBespeakVo.getTableid()==2){
                    maxamount = resourceSiteVo.getMaxamount();
                }
                for (Integer i = 0; i < maxamount; i++) {
                    jedisPool.lpush(activityKey, activityToken);
                }
                try {
                    bespeakDate = new SimpleDateFormat("yyyyMMdd").parse(resourceBespeakVo.getBespeakday());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //该令牌于预约日期23:59过期
                Integer remainSecondsManyDay = DateUtil.getRemainSecondsManyDay(new Date(),bespeakDate) - 60;
                jedisPool.expire(activityKey,remainSecondsManyDay);
            }
            //如果令牌池尚有足够数量令牌，则删除预约数量令牌并添加预约记录，否则返回剩余名额数量
            if (jedisPool.llen(activityKey)<bespeakamount){
                result = JsonResult.getFail("预约人数大于剩余名额数量："+jedisPool.llen(activityKey));
            }else {
                jedisPool.lrem(activityKey,bespeakamount,activityToken);
                //往数据库插入预约记录
                if (resourceBespeakService.insertResourceBespeak(resourceBespeakVo)){
                    result = JsonResult.getSuccess("success");
                }else {
                    result = JsonResult.getFail("预约失败");
                }
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("修改预约接口")
    //该接口主要用于修改status 1 正常  2 作废  3 已使用
    @PostMapping("/updateBespeak")
    public JsonResult updateBespeak(@RequestBody ResourceBespeakVo resourceBespeakVo){
        JsonResult result;
        if (resourceBespeakVo!=null){
            //需要区分两种情况，一是用户进行作废操作，则必须传之前预约数量，并添加相应令牌到令牌池
            //如果是系统到了预约当天0时，将预约信息批量作废，则将预约数统一置为0，并直接修改数据状态；
            //预约当天0时，对应令牌池中令牌会根据之前设置过期时间自动过期删除，无需手动删除
            Integer bespeakamount = resourceBespeakVo.getBespeakamount();
            if (resourceBespeakVo.getStatus()==2&&bespeakamount!=null){
                String activityKey = RedisKey.ACTIVITY_INFO + resourceBespeakVo.getTableid() + resourceBespeakVo.getResourceid() + resourceBespeakVo.getBespeakday();
                String activityToken = "activityToken";
                JedisCluster jedisPool = getJedisPool();
                for (Integer i = 0; i < bespeakamount; i++) {
                    jedisPool.lpush(activityKey, activityToken);
                }
            }
            if (resourceBespeakService.updateResourceBespeak(resourceBespeakVo)){
                result = JsonResult.getSuccess("success");
            }else {
                result = JsonResult.getFail("操作失败");
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("查询预约接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceBespeakVo",
                    value ="参数实例：{\"startTime\":\"20200731150000\",\"endTime\":\"20200731160001\"}",
                    paramType="body",
                    dataType = "ResourceBespeakVo",
                    required=true )
    })
    @PostMapping("/findBespeakList/{pageNum}/{pageSize}")
    public JsonResult findBespeakList(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            @RequestBody(required = false) ResourceBespeakVo resourceBespeakVo
            ){
        JsonResult result;
        PageHelper.startPage(pageNum,pageSize);
        List<ResourceBespeakVo> resourceBespeakVos = resourceBespeakService.queryAllRows(resourceBespeakVo);
        PageInfo pageInfo = new PageInfo<>(resourceBespeakVos);

        //查询预约的同时获取预约关联的活动或场馆信息
        List tmpResourceBespeakVos = new ArrayList<>();
        for (ResourceBespeakVo tmpVo : resourceBespeakVos) {
            if (tmpVo.getTableid()==1){
                tmpVo.setResourceActivityVo(resourceActivityService.selectByPrimaryKey(tmpVo.getResourceid()));
                tmpResourceBespeakVos.add(tmpVo);
            }else {
                tmpVo.setResourceSiteVo(resourceSiteService.selectByPrimaryKey(tmpVo.getResourceid()));
                tmpResourceBespeakVos.add(tmpVo);
            }
        }
        pageInfo.setList(tmpResourceBespeakVos);
        result = JsonResult.getSuccess(pageInfo);
        return result;
    }


}
