package com.zte.service;


import com.zte.bean.dto.AuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.bean.ResourceActivity;
import com.zte.bean.vo.ResourceActivityVo;
import com.zte.dao.ResourceActivityMapper;

import java.util.List;

@Service
public class ResourceActivityService {
	
	@Autowired
	ResourceActivityMapper resourceActivityMapper;
	
	public Integer insertResourceActivity(ResourceActivityVo record) {
//		record.setStatus(2);
//		String currentTime = DateUtil.getDBDatetime();
//		record.setCreatetime(currentTime);
//		record.setUpdatetime(currentTime);
		resourceActivityMapper.insertSelective(record);
		return record.getResourceid();
	}
	

	public boolean updateResourceActivity(ResourceActivityVo record) {
		int i=resourceActivityMapper.updateByPrimaryKeySelective(record);
		return i==1?true:false;
	}
	
	
	public ResourceActivityVo selectByPrimaryKey(Integer resourceid) {
		
		return resourceActivityMapper.selectByPrimaryKey(resourceid);
	}
	
	public List<ResourceActivityVo> selectAll(ResourceActivityVo record) {

		 return resourceActivityMapper.selectAll(record);
	}

	public Boolean updateByActivityIds(AuditDto auditDto){
		return resourceActivityMapper.updateByPrimaryKeysSelective(auditDto)==auditDto.getResourceIds().size()?true:false;
	}
}
