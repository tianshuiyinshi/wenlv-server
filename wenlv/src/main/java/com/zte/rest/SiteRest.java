package com.zte.rest;



import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zte.bean.ResourceNews;
import com.zte.bean.SysLabel;
import com.zte.bean.dto.AuditDto;
import com.zte.bean.vo.ResourceActivityVo;
import com.zte.bean.vo.ResourceLabelVo;
import com.zte.service.ResourceLabelService;
import com.zte.service.SysLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.zte.bean.ResourceSite;
import com.zte.bean.vo.AdminVo;
import com.zte.bean.vo.ResourceSiteVo;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.SystemUtils;
import com.zte.service.ResourceSiteService;

import java.util.ArrayList;
import java.util.List;

/**
 *  场馆
 * @author songyong
 *
 */
@Api(tags = "场馆资源操作类")
@RestController
@RequestMapping("/resourceSite")
public class SiteRest {
	
	@Autowired
	ResourceSiteService resourceSiteService;

	@Autowired
	ResourceLabelService resourceLabelService;

	@Autowired
	SysLabelService sysLabelService;
	
	@Autowired
	HttpServletRequest req;
	
	/**
	 * 新增新闻资讯
	 * 可根据id、分类、状态查询、标题模糊查询
	 * @param record
	 * @return
	 */
	@ApiOperation("场馆资源查询接口")
	@PostMapping("/findSite/{pageNum}/{pageSize}")
	public JsonResult findSite(
			@PathVariable("pageNum")Integer pageNum,
			@PathVariable("pageSize")Integer pageSize,
			@RequestBody(required = false) ResourceSiteVo record) {


		PageHelper.startPage(pageNum, pageSize);
		List<ResourceSiteVo> resourceSiteVos = resourceSiteService.selectAll(record);
		PageInfo<ResourceSiteVo> pageInfoForResult = new PageInfo<>(resourceSiteVos);

		List<ResourceSiteVo> resourceSiteVosForResult = new ArrayList<>();
		for (ResourceSiteVo resourceSiteVo : resourceSiteVos) {
			List<ResourceLabelVo> resourceLabelVosForResult = new ArrayList<>();
			ResourceLabelVo resourceLabelVoForQuery = new ResourceLabelVo();
			resourceLabelVoForQuery.setResourceid(resourceSiteVo.getResourceid());
			resourceLabelVoForQuery.setTableid(2);
			List<ResourceLabelVo> resourceLabelVosForCircle = resourceLabelService.queryAllRows(resourceLabelVoForQuery);
			for (ResourceLabelVo labelVo : resourceLabelVosForCircle) {
				SysLabel sysLabel = sysLabelService.querySysLabelById(labelVo.getLabelid());
				labelVo.setSysLabel(sysLabel);
				resourceLabelVosForResult.add(labelVo);
			}
			resourceSiteVo.setResourceLabelVos(resourceLabelVosForResult);
			resourceSiteVosForResult.add(resourceSiteVo);
		}
		pageInfoForResult.setList(resourceSiteVosForResult);

		return JsonResult.getSuccess(pageInfoForResult);
	}
	
	/**
	 * 新增新闻资讯
	 * @param body
	 * @return
	 */
	@ApiOperation("场馆资源新增接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="body",
					value ="实例：{\"record\":\\{\"resourcetitle\":\"test\"},\"labelIds\":[2,3]}",
					required=true )
	})
	@PostMapping("/addSite")
	public JsonResult addSite(@RequestBody JSONObject body) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);
		if (body!=null&&body.containsKey("record")){
			ResourceSiteVo record = body.getObject("record", ResourceSiteVo.class);
			List<Integer> labelIds=null;
			if (body.containsKey("labelIds")){
				labelIds = body.getObject("labelIds", List.class);
			}
			if(admin!=null&&record!=null){
				String currentTime = DateUtil.getDBDatetime();
				record.setCreator(admin.getAdminId());
				record.setUpdater(admin.getAdminId());
				record.setCreatetime(currentTime);
				record.setUpdatetime(currentTime);
				record.setStatus(2);
				Integer resourceid = resourceSiteService.insertResourceSite(record);
				if (labelIds!=null&&labelIds.size()!=0){
					ResourceLabelVo resourceLabelVo = new ResourceLabelVo();
					resourceLabelVo.setCreator(admin.getAdminId());
					resourceLabelVo.setUpdater(admin.getAdminId());
					resourceLabelVo.setCreatetime(currentTime);
					resourceLabelVo.setUpdatetime(currentTime);
					resourceLabelVo.setTableid(2);
					resourceLabelVo.setResourceid(resourceid);
					resourceLabelVo.setStatus(1);
					for (Integer labelId : labelIds) {
						resourceLabelVo.setLabelid(labelId);
						resourceLabelService.addResourceLabel(resourceLabelVo);
					}
				}
				result = JsonResult.getSuccess("success");
			}else {
				result = JsonResult.getFail("参数内容为空");
			}
		}else {
			result = JsonResult.getFail("参数为空");
		}
		return result;
	}
	


	/**
	 * 修改新闻资讯
	 * @param body
	 * @return
	 */
	@ApiOperation("场馆资源修改接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="body",
					value ="实例：{\"record\":\\{\"resourcetitle\":\"test\",\"resourceid\":13},\"labelIds\":[2,3]}",
					required=true )
	})
	@PostMapping("/updateSite")
	public JsonResult updateSite(@RequestBody JSONObject body) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);
		String currentTime = DateUtil.getDBDatetime();

		if(body!=null&&body.containsKey("record")){
			ResourceSiteVo record = body.getObject("record", ResourceSiteVo.class);
			List<Integer> labelIds=null;
			if (body.containsKey("labelIds")){
				labelIds = body.getObject("labelIds", ArrayList.class);
			}
			if(record.getStatus()!=null&&(record.getStatus()==1||record.getStatus()==3)) {
				//如果是审核，则添加审核人及审核时间
				record.setAuditor(admin.getAdminId());
				record.setAudittime(currentTime);
			}else if(record.getUpdatetime()!=null){
				record.setUpdater(admin.getAdminId());
			}else {
				record.setUpdatetime(null);
				record.setUpdater(admin.getAdminId());
			}
			//如果有标签id列表
			if (labelIds!=null&&labelIds.size()!=0){
				Integer resourceid = record.getResourceid();
				if (resourceid==null||resourceSiteService.selectByPrimaryKey(resourceid)==null){
					return JsonResult.getFail("未获取到正确的resourceid");
				}
				ResourceLabelVo resourceLabelVo = new ResourceLabelVo();
				resourceLabelVo.setCreator(admin.getAdminId());
				resourceLabelVo.setUpdater(admin.getAdminId());
				resourceLabelVo.setCreatetime(currentTime);
				resourceLabelVo.setUpdatetime(currentTime);
				resourceLabelVo.setTableid(2);
				resourceLabelVo.setResourceid(resourceid);
				resourceLabelVo.setStatus(1);
				resourceLabelService.deleteResourceLabelByResourceId(resourceid);
				for (Integer labelId : labelIds) {
					resourceLabelVo.setLabelid(labelId);
					resourceLabelService.addResourceLabel(resourceLabelVo);
				}
			}
			resourceSiteService.updateResourceSite(record);
			result = JsonResult.getSuccess("success");

		}else {
			result = JsonResult.getFail("参数为空");
		}
		return result;
	}

	@ApiOperation("批量修改场馆资源状态接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="auditDto",
					value ="参数实例：{\"resourceIds\":[1,2],\"status\":2}",
					paramType="AuditDto",
					dataType = "AuditDto",
					required=true )
	})
	@PostMapping("/updateSites")
	public JsonResult updateSites(@RequestBody AuditDto auditDto) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);
		String currentTime = DateUtil.getDBDatetime();

		if (auditDto!=null&&auditDto.getStatus()!=null) {
			if (auditDto.getStatus() != 1 && auditDto.getStatus() != 3) {
				result = JsonResult.getFail("审批状态不合规");
			} else {
				//如果是审核，则添加审核人及审核时间
				auditDto.setAuditor(admin.getAdminId());
				auditDto.setAudittime(currentTime);
				resourceSiteService.updateByResourceIds(auditDto);
				result = JsonResult.getSuccess("success");
			}
		}else {
			result =JsonResult.getFail("参数或状态为空");
		}
		return result;

	}

	@ApiOperation("调换两个资源的排序位置")
	@ApiImplicitParams({
			@ApiImplicitParam(name="records",value ="请求示例：[{\"updatetime\":\"20201001000000\"},{\"updatetime\":\"20201002000000\"}]\n")
	})
	@PostMapping("/updateTwoSiteLocation")
	public JsonResult updateTwoSiteLocation(@RequestBody List<ResourceSite> records) {
		JsonResult result;

		if (records!=null&&records.size()==2){
			ResourceSite thisResourceSite = records.get(0);
			ResourceSite otherResourceSite = records.get(1);
			String thisUpdatetime = thisResourceSite.getUpdatetime();
			thisResourceSite.setUpdatetime(otherResourceSite.getUpdatetime());
			otherResourceSite.setUpdatetime(thisUpdatetime);
			//调用 updateActivity 进行调换
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("record",thisResourceSite);
			updateSite(jsonObject);
			jsonObject.put("record",otherResourceSite);
			updateSite(jsonObject);
			result = JsonResult.getSuccess("success");
		}else {
			result = JsonResult.getFail("需要选中两个资源");
		}

		return result;
	}
	
	
	
	
}
