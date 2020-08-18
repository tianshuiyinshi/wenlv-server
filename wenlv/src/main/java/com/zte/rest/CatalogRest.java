package com.zte.rest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.bean.vo.AdminVo;
import com.zte.bean.vo.CatalogResourceVo;
import com.zte.bean.vo.CatalogVo;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.SystemUtils;
import com.zte.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-08-11 19:56
 */
@Api(tags = "栏目操作类")
@RestController
@RequestMapping("/catalog")
public class CatalogRest {

    @Autowired
    CatalogService catalogService;

    @Autowired
    CatalogResourceService catalogResourceService;

    @Autowired
    ResourceNewsService resourceNewsService;

    @Autowired
    ResourceActivityService resourceActivityService;

    @Autowired
    ResourceSiteService resourceSiteService;




    @Autowired
    HttpServletRequest req;

    @ApiOperation("新增栏目接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="catalogVo",value ="catalogVo",paramType="CatalogVo",dataType = "CatalogVo",required=false )
    })
    @PostMapping("/addCatalog")
    public JsonResult addCatalog(@RequestBody CatalogVo catalogVo){
        JsonResult result;
        AdminVo adminVo = SystemUtils.getAdminInfo(req);
        Integer adminId = adminVo.getAdminId();

        if (catalogVo!=null&&adminVo!=null){
            String currentTime = DateUtil.getDBDatetime();
            catalogVo.setCreator(adminId);
            catalogVo.setUpdater(adminId);
            catalogVo.setCreatetime(currentTime);
            catalogVo.setUpdatetime(currentTime);
            catalogVo.setStatus(2);
            Integer catalogId = catalogService.addCatalog(catalogVo);
            if (catalogVo.getCatalogResourceVos()!=null&&catalogVo.getCatalogResourceVos().size()!=0){
                for (CatalogResourceVo catalogResourceVo : catalogVo.getCatalogResourceVos()) {
                    catalogResourceVo.setCatalogid(catalogId);
                    catalogResourceVo.setCreator(adminId);
                    catalogResourceVo.setUpdater(adminId);
                    catalogResourceVo.setCreatetime(currentTime);
                    catalogResourceVo.setUpdatetime(currentTime);
                    catalogResourceVo.setStatus(1);
                    catalogResourceService.addCatalogResource(catalogResourceVo);
                }
            }
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("修改栏目接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="catalogVo",value ="catalogVo",paramType="CatalogVo",dataType = "CatalogVo",required=false )
    })
    @PostMapping("/updateCatalog")
    public JsonResult updateCatalog(@RequestBody CatalogVo catalogVo){
        JsonResult result;
        AdminVo adminVo = SystemUtils.getAdminInfo(req);
        Integer adminId = adminVo.getAdminId();

        if (catalogVo!=null&&adminVo!=null){
            String currentTime = DateUtil.getDBDatetime();
            catalogVo.setUpdater(adminId);
            catalogVo.setUpdatetime(currentTime);
            catalogService.updateCatalog(catalogVo);
            if (catalogVo.getCatalogResourceVos()!=null&&catalogVo.getCatalogResourceVos().size()!=0){
                for (CatalogResourceVo catalogResourceVo : catalogVo.getCatalogResourceVos()) {
                    catalogResourceVo.setUpdater(adminVo.getAdminId());
                    //如果之前栏目有该资讯绑定，则更新；若没有则新增栏目资讯
                    if (catalogResourceService.queryAllRows(catalogResourceVo)!=null){
                        //如果没带时间值，则默认系统时间；如果带了时间，则赋值为该时间，方便排序变化
                        if (catalogResourceVo.getUpdatetime()==null){
                            catalogResourceVo.setUpdatetime(currentTime);
                        }
                        catalogResourceService.updateCatalogResource(catalogResourceVo);
                    }else {
                        catalogResourceVo.setCreator(adminId);
                        catalogResourceVo.setUpdater(adminId);
                        catalogResourceVo.setCreatetime(currentTime);
                        catalogResourceVo.setUpdatetime(currentTime);
                        catalogResourceVo.setStatus(1);
                        catalogResourceService.addCatalogResource(catalogResourceVo);
                    }
                }
            }
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }


    @ApiOperation("栏目查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="catalogVo",value ="catalogVo",paramType="CatalogVo",dataType = "CatalogVo",required=false )
    })
    @PostMapping("/findCatalogList/{pageNum}/{pageSize}")
    public JsonResult findCatalogList(@PathVariable("pageNum") Integer pageNum,
                                      @PathVariable("pageSize") Integer pageSize,
                                      @RequestBody(required = false) CatalogVo catalogVo){
        PageHelper.startPage(pageNum,pageSize);
        List<CatalogVo> catalogVos = catalogService.queryAllRows(catalogVo);
        PageInfo<CatalogVo> catalogVoPageInfo = new PageInfo<>(catalogVos);

        List resultCatalogVos = new ArrayList<>();

        //将栏目信息，栏目资讯信息一同查出
        if (catalogVos!=null){
            for (CatalogVo resultCatalogVo : catalogVos) {
                ArrayList resultCatalogResourceVos = new ArrayList<>();
                CatalogResourceVo CatalogResourceVoForQuery = new CatalogResourceVo();
                CatalogResourceVoForQuery.setCatalogid(resultCatalogVo.getCatalogid());
                List<CatalogResourceVo> catalogResourceVos = catalogResourceService.queryAllRows(CatalogResourceVoForQuery);
                for (CatalogResourceVo catalogResourceVo : catalogResourceVos) {
                    Integer tableid = catalogResourceVo.getTableid();
                    Integer resourceid = catalogResourceVo.getResourceid();
                    //tableid=1 活动
                    if (tableid==1){
                        catalogResourceVo.setResourceActivityVo(resourceActivityService.selectByPrimaryKey(resourceid));
                        resultCatalogResourceVos.add(catalogResourceVo);
                    }
                    //tableid=2 场馆
                    if (tableid==2){
                        catalogResourceVo.setResourceSiteVo(resourceSiteService.selectByPrimaryKey(resourceid));
                        resultCatalogResourceVos.add(catalogResourceVo);
                    }
                    //tableid=3 资讯
                    if (tableid==3){
                        catalogResourceVo.setResourceNewsVo(resourceNewsService.selectByPrimaryKey(resourceid));
                        resultCatalogResourceVos.add(catalogResourceVo);
                    }
                }
                resultCatalogVo.setCatalogResourceVos(resultCatalogResourceVos);
                resultCatalogVos.add(resultCatalogVo);
            }
        }
        catalogVoPageInfo.setList(resultCatalogVos);
        return JsonResult.getSuccess(catalogVoPageInfo);
    }
}
