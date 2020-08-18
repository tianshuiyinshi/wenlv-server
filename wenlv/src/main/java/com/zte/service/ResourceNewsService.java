package com.zte.service;

import java.util.List;

import com.zte.bean.dto.AuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.bean.ResourceNews;
import com.zte.bean.vo.ResourceNewsVo;
import com.zte.common.utils.DateUtil;
import com.zte.dao.ResourceNewsMapper;

@Service
public class ResourceNewsService {
	
	@Autowired
	ResourceNewsMapper resourceNewsMapper;
	
	public Integer insertResourceNews(ResourceNewsVo record) {
//		record.setStatus(2);
//		String currentTime = DateUtil.getDBDatetime();
//		record.setCreatetime(currentTime);
//		record.setUpdatetime(currentTime);
		resourceNewsMapper.insertSelective(record);
		return record.getResourceid();
	}
	

	public boolean updateResourceNews(ResourceNews record) {
		int i=resourceNewsMapper.updateByPrimaryKeySelective(record);
		return i==1?true:false;
	}
	
	
	public ResourceNewsVo selectByPrimaryKey(Integer resourceid) {
		
		return resourceNewsMapper.selectByPrimaryKey(resourceid);
	}
	
	public List<ResourceNewsVo> selectAll(ResourceNewsVo record) {
		 return resourceNewsMapper.selectAll(record);
	}

	public Boolean updateByNewsIds(AuditDto auditDto){
		return resourceNewsMapper.updateByPrimaryKeysSelective(auditDto)==auditDto.getResourceIds().size()?true:false;
	}
}
