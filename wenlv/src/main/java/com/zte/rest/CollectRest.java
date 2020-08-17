package com.zte.rest;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.bean.vo.ResourceCollectVo;
import com.zte.common.utils.JsonResult;
import com.zte.service.ResourceActivityService;
import com.zte.service.ResourceCollectService;
import com.zte.service.ResourceNewsService;
import com.zte.service.ResourceSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-08-12 14:54
 */

@Api(tags = "收藏操作类")
@RestController
@RequestMapping("/collect")
public class CollectRest {

    @Autowired
    ResourceCollectService resourceCollectService;

    @Autowired
    ResourceActivityService resourceActivityService;

    @Autowired
    ResourceNewsService resourceNewsService;

    @Autowired
    ResourceSiteService resourceSiteService;

    @ApiOperation("新增收藏接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceCollectVo",value ="resourceCollectVo",paramType="ResourceCollectVo",dataType = "ResourceCollectVo",required=false )
    })
    @PostMapping("/addResourceCollect")
    public JsonResult addResourceCollect(@RequestBody ResourceCollectVo resourceCollectVo){
           JsonResult result;
           if (resourceCollectVo!=null){
               ResourceCollectVo resourceCollectVoForQuery = new ResourceCollectVo();

               resourceCollectVoForQuery.setResourceid(resourceCollectVo.getResourceid());
               resourceCollectVoForQuery.setUserid(resourceCollectVo.getUserid());
               resourceCollectVoForQuery.setTableid(resourceCollectVo.getTableid());

               if (resourceCollectService.queryAllRows(resourceCollectVoForQuery)!=null){
                   result = JsonResult.getFail("该资源已被收藏");
               }else {
                   resourceCollectService.addResourceCollect(resourceCollectVo);
                   result = JsonResult.getSuccess("success");
               }
           }else {
               result = JsonResult.getFail("参数为空");
           }
           return result;
    }

    @ApiOperation("删除收藏接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceCollectVo",value ="resourceCollectVo",paramType="ResourceCollectVo",dataType = "ResourceCollectVo",required=false )
    })
    @PostMapping("/deleteResourceCollect")
    public JsonResult deleteResourceCollect(@RequestBody ResourceCollectVo resourceCollectVo){
           JsonResult result;
           if (resourceCollectVo!=null&&resourceCollectVo.getCollectid()!=null){
               if(resourceCollectService.deleteResourceCollect(resourceCollectVo.getCollectid())){
                   result = JsonResult.getSuccess("success");
               }else {
                   result = JsonResult.getFail("该记录不存在");
               }
           }else {
               result = JsonResult.getFail("参数为空");
           }

           return result;
    }

    @ApiOperation("查询收藏接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceCollectVo",
                    value ="resourceCollectVo",
                    paramType="ResourceCollectVo",
                    dataType = "ResourceCollectVo",
                    required=false )
    })
    @PostMapping("/findResourceCollectList/{pageNum}/{pageSize}")
    public JsonResult findResourceCollectList(@PathVariable("pageNum") Integer pageNum,
                                              @PathVariable("pageSize") Integer pageSize,
                                              @RequestBody(required = false) ResourceCollectVo resourceCollectVo){
        PageHelper.startPage(pageNum,pageSize);
        List<ResourceCollectVo> resourceCollectVos = resourceCollectService.queryAllRows(resourceCollectVo);
        PageInfo<ResourceCollectVo> resourceCollectVoPageInfo = new PageInfo<>(resourceCollectVos);
        List resultResourceCollectVos  = new ArrayList();

        for (ResourceCollectVo collectVo : resourceCollectVos) {
            Integer tableid = collectVo.getTableid();
            Integer resourceid = collectVo.getResourceid();
            //tableid=1 活动
            if (tableid==1){
                collectVo.setResourceActivityVo(resourceActivityService.selectByPrimaryKey(resourceid));
                resultResourceCollectVos.add(collectVo);
            }
            //tableid=2 场馆
            if (tableid==2){
                collectVo.setResourceSiteVo(resourceSiteService.selectByPrimaryKey(resourceid));
                resultResourceCollectVos.add(collectVo);
            }
            //tableid=3 资讯
            if (tableid==3){
                collectVo.setResourceNewsVo(resourceNewsService.selectByPrimaryKey(resourceid));
                resultResourceCollectVos.add(collectVo);
            }
        }
        resourceCollectVoPageInfo.setList(resourceCollectVos);

        return JsonResult.getSuccess(resourceCollectVoPageInfo);
    }
}
