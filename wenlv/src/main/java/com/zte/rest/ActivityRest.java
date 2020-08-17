package com.zte.rest;


import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zte.bean.SysLabel;
import com.zte.bean.dto.AuditDto;
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
import com.zte.bean.ResourceActivity;
import com.zte.bean.vo.AdminVo;
import com.zte.bean.vo.ResourceActivityVo;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.SystemUtils;
import com.zte.service.ResourceActivityService;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "活动资源操作类")
@RestController
@RequestMapping("/activity")
public class ActivityRest {
	
	@Autowired
	ResourceActivityService resourceActivityService;

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
	@ApiOperation("查询活动资源接口")
	@PostMapping("/findActivity/{pageNum}/{pageSize}")
	public JsonResult findActivity(
			@PathVariable("pageNum")Integer pageNum,
			@PathVariable("pageSize")Integer pageSize,
			@RequestBody(required = false) ResourceActivityVo record) {
		PageHelper.startPage(pageNum,pageSize);
		List<ResourceActivityVo> resourceActivityVos = resourceActivityService.selectAll(record);
    	PageInfo<ResourceActivityVo> pageInfoForResult = new PageInfo<>(resourceActivityVos);

		List<ResourceActivityVo> resourceActivityVosForResult = new ArrayList<>();
		for (ResourceActivityVo resourceActivityVo : resourceActivityVos) {
			List<ResourceLabelVo> resourceLabelVosForResult = new ArrayList<>();
			ResourceLabelVo resourceLabelVoForQuery = new ResourceLabelVo();
			resourceLabelVoForQuery.setResourceid(resourceActivityVo.getResourceid());
			resourceLabelVoForQuery.setTableid(1);
			List<ResourceLabelVo> resourceLabelVosForCircle = resourceLabelService.queryAllRows(resourceLabelVoForQuery);
			for (ResourceLabelVo labelVo : resourceLabelVosForCircle) {
				SysLabel sysLabel = sysLabelService.querySysLabelById(labelVo.getLabelid());
				labelVo.setSysLabel(sysLabel);
				resourceLabelVosForResult.add(labelVo);
			}
			resourceActivityVo.setResourceLabelVos(resourceLabelVosForResult);
			resourceActivityVosForResult.add(resourceActivityVo);
		}
		pageInfoForResult.setList(resourceActivityVosForResult);

		return JsonResult.getSuccess(pageInfoForResult);
	}
	
	/**
	 * 新增新闻资讯
	 * @param record
	 * @return
	 */
	@ApiOperation("新增活动资源接口")
	@PostMapping("/addActivity")
	public JsonResult addActivity(@RequestBody ResourceActivity record) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);
		if(admin!=null&&record!=null){
			String currentTime = DateUtil.getDBDatetime();
			record.setCreator(admin.getAdminId());
			record.setUpdater(admin.getAdminId());
			record.setCreatetime(currentTime);
			record.setUpdatetime(currentTime);
			resourceActivityService.insertResourceActivity(record);
			result = JsonResult.getSuccess("success");
		}else {
			result = JsonResult.getFail("参数为空");
		}

		return result;
	}
	
	
	/**
	 * 修改新闻资讯
	 * @param record
	 * @return
	 */
	@ApiOperation("修改活动资源接口")
	@PostMapping("/updateActivity")
	public JsonResult updateActivity(@RequestBody ResourceActivity record) {
		AdminVo admin=SystemUtils.getAdminInfo(req);
		String currentTime = DateUtil.getDBDatetime();

		if(record.getStatus()==1&&record.getStatus()==3) {
			//如果是审核，则添加审核人及审核时间
			record.setAuditor(admin.getAdminId());
			record.setAudittime(currentTime);
		}else if(record.getUpdatetime()!=null){
			record.setUpdater(admin.getAdminId());
		}else {
			record.setUpdater(admin.getAdminId());
			record.setUpdatetime(null);
		}
		resourceActivityService.updateResourceActivity(record);
		return JsonResult.getSuccess("success");
	}

	/**
	 * @author yinsiwei
	 * @date 2020-08-15 16:42
	 * 用于批量审核
	*/

	@ApiOperation("批量修改活动资源状态接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="auditDto",
					value ="参数实例：{\"resourceIds\":[1,2],\"status\":2}",
					paramType="AuditDto",
					dataType = "AuditDto",
					required=true )
	})
	@PostMapping("/updateActivitys")
	public JsonResult updateActivitys(@RequestBody AuditDto auditDto) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);
		String currentTime = DateUtil.getDBDatetime();

		if (auditDto!=null&&auditDto.getStatus()!=null&&auditDto.getResourceIds()!=null){
			//如果是审核，则添加审核人及审核时间
			auditDto.setAuditor(admin.getAdminId());
			auditDto.setAudittime(currentTime);
			resourceActivityService.updateByActivityIds(auditDto);
			result = JsonResult.getSuccess("success");
		}else {
			result = JsonResult.getFail("参数或状态为空");
		}
		return result;
	}




	@ApiOperation("调换两个资源的排序位置")
	@ApiImplicitParams({
			@ApiImplicitParam(name="records",value ="请求示例：[{\"updatetime\":\"20201001000000\"},{\"updatetime\":\"20201002000000\"}]\n" )
	})
	@PostMapping("/updateTwoActivityLocation")
	public JsonResult updateTwoActivityLocation(@RequestBody List<ResourceActivity> records) {
		JsonResult result;

		if (records!=null&&records.size()==2){
			ResourceActivity thisResourceActivity = records.get(0);
			ResourceActivity otherResourceActivity = records.get(1);
			String thisUpdatetime = thisResourceActivity.getUpdatetime();
			thisResourceActivity.setUpdatetime(otherResourceActivity.getUpdatetime());
			otherResourceActivity.setUpdatetime(thisUpdatetime);
			updateActivity(thisResourceActivity);
			updateActivity(otherResourceActivity);
			result = JsonResult.getSuccess("success");
		}else {
			result = JsonResult.getFail("需要选中两个资源");
		}

		return result;
	}
	
	
	
	
}
