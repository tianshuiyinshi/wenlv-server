package com.zte.rest;


import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zte.bean.ResourceActivity;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import com.zte.bean.ResourceNews;
import com.zte.bean.vo.AdminVo;
import com.zte.bean.vo.ResourceNewsVo;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.SystemUtils;
import com.zte.service.ResourceNewsService;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "资讯资源操作类")
@RestController
@RequestMapping("/resourceNews")
public class NewsRest {
	private static Logger log = LoggerFactory.getLogger(SystemUtils.class);


	@Autowired
	ResourceNewsService resourceNewsService;

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
	@ApiOperation("资讯资源查询接口")
	@PostMapping("/findNews/{pageNum}/{pageSize}")
	public JsonResult findNews(
			@PathVariable("pageNum")Integer pageNum,
			@PathVariable("pageSize")Integer pageSize,
			@RequestBody(required = false) ResourceNewsVo record) {

		PageHelper.startPage(pageNum,pageSize);
		List<ResourceNewsVo> resourceNewsVos = resourceNewsService.selectAll(record);
		PageInfo<ResourceNewsVo> pageInfoForResult = new PageInfo<>(resourceNewsVos);

		List<ResourceNewsVo> resourceNewsForResult = new ArrayList<>();
		for (ResourceNewsVo resourceNewsVo : resourceNewsVos) {
			List<ResourceLabelVo> resourceLabelVosForResult = new ArrayList<>();
			ResourceLabelVo resourceLabelVoForQuery = new ResourceLabelVo();
			resourceLabelVoForQuery.setResourceid(resourceNewsVo.getResourceid());
			resourceLabelVoForQuery.setTableid(3);
			List<ResourceLabelVo> resourceLabelVosForCircle = resourceLabelService.queryAllRows(resourceLabelVoForQuery);
			for (ResourceLabelVo labelVo : resourceLabelVosForCircle) {
				SysLabel sysLabel = sysLabelService.querySysLabelById(labelVo.getLabelid());
				labelVo.setSysLabel(sysLabel);
				resourceLabelVosForResult.add(labelVo);
			}
			resourceNewsVo.setResourceLabelVos(resourceLabelVosForResult);
			resourceNewsForResult.add(resourceNewsVo);
		}
		pageInfoForResult.setList(resourceNewsForResult);
		return JsonResult.getSuccess(pageInfoForResult);
	}
	
	/**
	 * 新增新闻资讯
	 * @param body
	 * @return
	 */
	@ApiOperation("资讯资源新增接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="body",
					value ="实例：{\"record\":\\{\"resourcetitle\":\"test\"},\"labelIds\":[2,3]}",
					required=true )
	})
	@PostMapping("/addNews")
	public JsonResult addNews(@RequestBody JSONObject body) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);
		if (body!=null&&body.containsKey("record")){
			ResourceNewsVo record = body.getObject("record", ResourceNewsVo.class);
			List<Integer> labelIds=null;
			if (body.containsKey("labelIds")){
				labelIds = body.getObject("labelIds", ArrayList.class);
			}
			if(admin!=null&&record!=null){
				String currentTime = DateUtil.getDBDatetime();
				record.setCreator(admin.getAdminId());
				record.setUpdater(admin.getAdminId());
				record.setCreatetime(currentTime);
				record.setUpdatetime(currentTime);
				record.setStatus(2);
				Integer resourceid = resourceNewsService.insertResourceNews(record);
				if (labelIds!=null&&labelIds.size()!=0){
					ResourceLabelVo resourceLabelVo = new ResourceLabelVo();
					resourceLabelVo.setCreator(admin.getAdminId());
					resourceLabelVo.setUpdater(admin.getAdminId());
					resourceLabelVo.setCreatetime(currentTime);
					resourceLabelVo.setUpdatetime(currentTime);
					resourceLabelVo.setTableid(3);
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
	@ApiOperation("资讯资源修改接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="body",
					value ="实例：{\"record\":\\{\"resourcetitle\":\"test\",\"resourceid\":6},\"labelIds\":[2,3]}",
					required=true )
	})
	@PostMapping("/updateNews")
	public JsonResult updateNews(@RequestBody JSONObject body) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);
		String currentTime = DateUtil.getDBDatetime();

		if(body!=null&&body.containsKey("record")){
			ResourceNewsVo record = body.getObject("record", ResourceNewsVo.class);
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
				//如果有标签id列表
				if (labelIds.size()!=0){
					Integer resourceid = record.getResourceid();
					if (resourceid==null||resourceNewsService.selectByPrimaryKey(resourceid)==null){
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
			}
			resourceNewsService.updateResourceNews(record);
			result = JsonResult.getSuccess("success");

		}else {
			result = JsonResult.getFail("参数为空");
		}
		return result;
	}

	@ApiOperation("批量修改资讯资源状态接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="auditDto",
					value ="参数实例：{\"resourceIds\":[1,2],\"status\":2}",
					paramType="AuditDto",
					dataType = "AuditDto",
					required=true )
	})
	@PostMapping("/updateNewsList")
	public JsonResult updateNewsList(@RequestBody AuditDto auditDto) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);
		String currentTime = DateUtil.getDBDatetime();

		if (auditDto!=null&&auditDto.getStatus()!=null){
			if (auditDto.getStatus()!=1&&auditDto.getStatus()!=3){
				result = JsonResult.getFail("审批状态不合规");
			}else {
				//如果是审核，则添加审核人及审核时间
				auditDto.setAuditor(admin.getAdminId());
				auditDto.setAudittime(currentTime);
				resourceNewsService.updateByNewsIds(auditDto);
				result = JsonResult.getSuccess("success");
			}
		}else {
			result = JsonResult.getFail("参数或状态为空");
		}
		return result;
	}

	@ApiOperation("调换两个资源的排序位置")
	@ApiImplicitParams({
			@ApiImplicitParam(name="records",value ="请求示例：[{\"updatetime\":\"20201001000000\"},{\"updatetime\":\"20201002000000\"}]\n")
	})
	@PostMapping("/updateTwoNewsLocation")
	public JsonResult updateTwoNewsLocation(@RequestBody List<ResourceNews> records) {
		JsonResult result;

		if (records!=null&&records.size()==2){
			ResourceNews thisResourceNews = records.get(0);
			ResourceNews otherResourceNews = records.get(1);
			String thisUpdatetime = thisResourceNews.getUpdatetime();
			thisResourceNews.setUpdatetime(otherResourceNews.getUpdatetime());
			otherResourceNews.setUpdatetime(thisUpdatetime);
			//调用 updateActivity 进行调换
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("record",thisResourceNews);
			updateNews(jsonObject);
			jsonObject.put("record",otherResourceNews);
			updateNews(jsonObject);
			result = JsonResult.getSuccess("success");
		}else {
			result = JsonResult.getFail("需要选中两个资源");
		}

		return result;
	}
	
	
	
	
}
