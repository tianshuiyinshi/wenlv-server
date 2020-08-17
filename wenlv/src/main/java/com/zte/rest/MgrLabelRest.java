package com.zte.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.bean.SysLabel;
import com.zte.bean.vo.AdminVo;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.SimpleUtil;
import com.zte.common.utils.SystemUtils;
import com.zte.service.ResourceLabelService;
import com.zte.service.SysLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-28 16:05
 */
@Api(tags = "标签操作类")
@RestController
@RequestMapping("/label")
public class MgrLabelRest {

    @Autowired
    SysLabelService sysLabelService;

    @Autowired
    ResourceLabelService resourceLabelService;

    @Autowired
    HttpServletRequest req;
    @ApiOperation("新增系统标签接口")
    @PostMapping("/sysLabel/addLabel")
    public JsonResult addLabel(@RequestBody SysLabel sysLabel){
        JsonResult result;
        AdminVo adminInfo = SystemUtils.getAdminInfo(req);
        if (sysLabel!=null&&adminInfo!=null){
            if (sysLabelService.querySysLabelByName(sysLabel.getLabelword())==null){
                String currentTime = DateUtil.getDBDatetime();
                sysLabel.setCreator(adminInfo.getAdminId());
                sysLabel.setUpdater(adminInfo.getAdminId());
                sysLabel.setUpdatetime(currentTime);
                sysLabel.setUpdatetime(currentTime);
                sysLabel.setStatus(1);
                sysLabelService.addSysLabel(sysLabel);
                result = JsonResult.getSuccess("success");
            }else {
                result = JsonResult.getFail("系统标签名重复");
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("删除系统标签接口")
    @PostMapping("/sysLabel/delLabelList")
    public JsonResult delLabelList(@RequestBody List<Integer> sysLabelIds){
        JsonResult result;
        if (sysLabelIds!=null&&sysLabelIds.size()>0){
            for (Integer sysLabelId : sysLabelIds) {
                resourceLabelService.deleteResourceLabelByLabelId(sysLabelId);
                sysLabelService.deleteSysLabel(sysLabelId);
            }
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("修改系统标签口")
    @PostMapping("/sysLabel/updateLabel")
    public JsonResult updateLabel(@RequestBody SysLabel sysLabel){
        JsonResult result;
        String currentTime = DateUtil.getDBDatetime();
        AdminVo adminInfo = SystemUtils.getAdminInfo(req);
        if (sysLabel!=null){
            sysLabel.setUpdater(adminInfo.getAdminId());
            sysLabel.setUpdatetime(currentTime);
            sysLabelService.updateSysLabel(sysLabel);
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }
    @ApiOperation("查询系统标签接口")
    @PostMapping("/sysLabel/findLabelList/{pageNum}/{pageSize}")
    public JsonResult findLabelList(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,@RequestBody(required = false) SysLabel sysLabel){
        JsonResult result;
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<SysLabel> sysLabelPageInfo = new PageInfo<>(sysLabelService.queryAllRows(sysLabel));
        result = JsonResult.getSuccess(sysLabelPageInfo);
        return result;
    }



}
