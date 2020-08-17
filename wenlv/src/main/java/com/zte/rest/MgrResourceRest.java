package com.zte.rest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.bean.ResourceType;
import com.zte.bean.vo.AdminVo;
import com.zte.bean.vo.ResourceTypeVo;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.SystemUtils;
import com.zte.service.AdminService;
import com.zte.service.ResourceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yinsiwei
 * @date 2020-07-27 17:37
 */
@Api(tags = "资源类型操作类")
@RestController
@RequestMapping("/resourceType")
public class MgrResourceRest {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    ResourceTypeService resourceTypeService;
    
    @Autowired
    HttpServletRequest req;

    @ApiOperation("查询资源类型列表接口")
    @PostMapping("/findResourceTypeList/{pageNum}/{pageSize}")
    public JsonResult findResourceTypeList(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,@RequestBody(required = false) ResourceTypeVo resourceTypeVo){
        JsonResult result;
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        List<ResourceTypeVo> resourceTypeVos = resourceTypeService.queryAllRows(resourceTypeVo);
        PageInfo<ResourceTypeVo> resourceTypePageInfo = new PageInfo<>(resourceTypeVos);
        result = JsonResult.getSuccess(resourceTypePageInfo);
        return result;
    }



    @ApiOperation("新增资源类型接口")
    @PostMapping("/addResourceType")
    public JsonResult addResourceType(@RequestBody ResourceType resourceType){
        JsonResult result;
        if (resourceType!=null){
            if (resourceTypeService.queryResourceTypeByName(resourceType.getResourtypename())!=null){
                result = JsonResult.getFail("资源类型名称重复");
            }else {
                //添加创建用户和更新用户
                AdminVo adminVo = SystemUtils.getAdminInfo(req);
                String currentTime = DateUtil.getDBDatetime();
                resourceType.setCreator(adminVo.getAdminId());
                resourceType.setUpdater(adminVo.getAdminId());
                resourceType.setCreatetime(currentTime);
                resourceType.setUpdatetime(currentTime);
                resourceType.setStatus(1);
                resourceTypeService.addResourceType(resourceType);
                result = JsonResult.getSuccess("success");
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("修改资源类型接口")
    @PostMapping("/updateResourceType")
    public JsonResult updateResourceType(@RequestBody ResourceType resourceType){
        JsonResult result;
        if (resourceType!=null){
            //添加更新时间
            String currentTime = DateUtil.getDBDatetime();
            resourceType.setUpdatetime(currentTime);
            //添加更新用户
            AdminVo adminInfo = SystemUtils.getAdminInfo(req);
            resourceType.setUpdater(adminInfo.getAdminId());
            resourceTypeService.updateResourceType(resourceType);
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }




}
