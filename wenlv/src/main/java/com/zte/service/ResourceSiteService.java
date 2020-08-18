package com.zte.service;

import java.util.List;

import com.zte.bean.dto.AuditDto;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.bean.ResourceSite;
import com.zte.bean.vo.ResourceSiteVo;
import com.zte.common.utils.DateUtil;
import com.zte.dao.ResourceSiteMapper;

@Service
public class ResourceSiteService {
	
	@Autowired
	ResourceSiteMapper resourceSiteMapper;
	
	public Integer insertResourceSite(ResourceSiteVo record) {
//		record.setStatus(2);
//		String currentTime = DateUtil.getDBDatetime();
//		record.setCreatetime(currentTime);
//		record.setUpdatetime(currentTime);
		resourceSiteMapper.insertSelective(record);
		return record.getResourceid();
	}
	

	public boolean updateResourceSite(ResourceSite record) {
		int i=resourceSiteMapper.updateByPrimaryKeySelective(record);
		return i==1?true:false;
	}
	
	
	public ResourceSiteVo selectByPrimaryKey(Integer resourceid) {
		
		return resourceSiteMapper.selectByPrimaryKey(resourceid);
	}
	
	public List<ResourceSiteVo> selectAll(ResourceSiteVo record) {
		 return resourceSiteMapper.selectAll(record);
	}

	public Boolean updateByResourceIds(AuditDto auditDto){
		return resourceSiteMapper.updateByPrimaryKeysSelective(auditDto)==auditDto.getResourceIds().size()?true:false;
	}
}
