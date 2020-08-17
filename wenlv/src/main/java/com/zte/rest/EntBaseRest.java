package com.zte.rest;

import com.alibaba.fastjson.JSONObject;
import com.zte.bean.EntBase;
import com.zte.common.utils.JsonResult;
import com.zte.service.EntBaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-22 10:46
 */
@RestController
@RequestMapping("/mgrEnt")
public class EntBaseRest {
    @Autowired
    EntBaseService entBaseService;

    @PostMapping("/findAll")
    public JsonResult findAllEnt(){
        JsonResult result;
        List<EntBase> entBases = entBaseService.queryAllEntBase();
        result=JsonResult.getSuccess(entBases);
        return result;
    }

    @PostMapping("findEntById")
    public JsonResult findEntById(@RequestBody JSONObject body){
        JsonResult result;
        if(body!=null&&body.containsKey("entId")&& StringUtils.isNotBlank(body.getString("entId"))){
            String entId = body.getString("entId");
            EntBase entBase = entBaseService.queryEntBaseById(entId);
            result=JsonResult.getSuccess(entBase);
            return result;
        }
        result=JsonResult.getFail("参数错误，添加失败");
        return result;
    }

    @PostMapping("/addEnt")
    public JsonResult addEnt(@RequestBody JSONObject body){
        JsonResult result;
        if(body!=null&&body.containsKey("entBase")){
            EntBase entBase = body.getObject("entBase", EntBase.class);
            if (entBaseService.queryEntBaseByFullName(entBase.getEntFullname())!=null){
                result=JsonResult.getFail("该机构名称已存在");
                return result;
            }else {
                entBaseService.addEntBase(entBase);
                Integer entId = entBaseService.queryEntBaseByFullName(entBase.getEntFullname()).getId();
                result=JsonResult.getSuccess(entId);
                return result;
            }
        }
        result=JsonResult.getFail("参数错误，添加失败");
        return result;

    }

    @PostMapping("/updateEnt")
    public JsonResult updateEnt(@RequestBody JSONObject body){
        JsonResult result;
        if(body!=null&&body.containsKey("entBase")){
            EntBase entBase = body.getObject("entBase", EntBase.class);
            if (entBaseService.queryEntBaseById(String.valueOf(entBase.getId()))!=null){
                result=JsonResult.getFail("该机构信息不存在");
                return result;
            }
            entBaseService.alterEntBase(entBase);
            result=JsonResult.getSuccess("success");
            return result;
        }
        result=JsonResult.getFail("参数错误，修改失败");
        return result;
    }

    @PostMapping("/delEnt")
    public JsonResult delEnt(@RequestBody JSONObject body){
        JsonResult result;
        if(body!=null&&body.containsKey("entid")){

            String entId = body.getString("entId");
            entBaseService.delEntBase(entId);
            result=JsonResult.getSuccess("success");
            return result;
        }
        result=JsonResult.getFail("参数错误，删除失败");
        return result;

    }

}
