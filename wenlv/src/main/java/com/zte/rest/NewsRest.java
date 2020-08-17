package com.zte.rest;


import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.zte.bean.ResourceActivity;
import com.zte.bean.SysLabel;
import com.zte.bean.dto.AuditDto;
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
			@RequestBody ResourceNewsVo record) {

		PageHelper.startPage(pageNum,pageSize);
		List<ResourceNewsVo> resourceNewsVos = resourceNewsService.selectAll(record);
		PageInfo<ResourceNewsVo> pageInfoForResult = new PageInfo<>(resourceNewsVos);

		List<ResourceNewsVo> resourceNewsForResult = new ArrayList<>();
		for (ResourceNewsVo resourceNewsVo : resourceNewsVos) {
			List<ResourceLabelVo> resourceLabelVosForResult = new ArrayList<>();
			ResourceLabelVo resourceLabelVoForQuery = new ResourceLabelVo();
			resourceLabelVoForQuery.setResourceid(resourceNewsVo.getResourceid());
			resourceLabelVoForQuery.setTableid(1);
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
	 * @param record
	 * @return
	 */
	@ApiOperation("资讯资源新增接口")
	@PostMapping("/addNews")
	public JsonResult addNews(@RequestBody ResourceNews record) {
		JsonResult result;
		AdminVo admin=SystemUtils.getAdminInfo(req);

		if (record!=null&&admin!=null){
			String currentTime = DateUtil.getDBDatetime();
			record.setCreator(admin.getAdminId());
			record.setUpdater(admin.getAdminId());
			record.setUpdatetime(currentTime);
			record.setCreatetime(currentTime);
			record.setStatus(1);
			resourceNewsService.insertResourceNews(record);
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
	@ApiOperation("资讯资源修改接口")
	@PostMapping("/updateNews")
	public JsonResult updateNews(@RequestBody ResourceNews record) {
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
		resourceNewsService.updateResourceNews(record);
		return JsonResult.getSuccess("success");
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
			//如果是审核，则添加审核人及审核时间
			auditDto.setAuditor(admin.getAdminId());
			auditDto.setAudittime(currentTime);
			resourceNewsService.updateByNewsIds(auditDto);
			result = JsonResult.getSuccess("success");
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
			updateNews(thisResourceNews);
			updateNews(otherResourceNews);
			result = JsonResult.getSuccess("success");
		}else {
			result = JsonResult.getFail("需要选中两个资源");
		}

		return result;
	}
	
	
	
	
}
