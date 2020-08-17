package com.zte.dao;

import java.util.List;

import com.zte.bean.ResourceActivity;
import com.zte.bean.dto.AuditDto;
import com.zte.bean.vo.ResourceActivityVo;

public interface ResourceActivityMapper {
    int deleteByPrimaryKey(Integer resourceid);

    int insert(ResourceActivity record);

    int insertSelective(ResourceActivity record);

    ResourceActivityVo selectByPrimaryKey(Integer resourceid);

    int updateByPrimaryKeySelective(ResourceActivity record);

    int updateByPrimaryKeyWithBLOBs(ResourceActivity record);

    int updateByPrimaryKey(ResourceActivity record);
    
    List<ResourceActivityVo> selectAll(ResourceActivityVo record);

    Integer updateByPrimaryKeysSelective(AuditDto auditDto);
}