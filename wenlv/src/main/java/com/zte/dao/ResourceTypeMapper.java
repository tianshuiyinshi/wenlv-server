package com.zte.dao;

import com.zte.bean.ResourceType;
import com.zte.bean.vo.ResourceTypeVo;

import java.util.List;

public interface ResourceTypeMapper {
    int deleteByPrimaryKey(Integer resourcetypeid);

    int insert(ResourceType record);

    int insertSelective(ResourceType record);

    ResourceType selectByPrimaryKey(Integer resourcetypeid);

    int updateByPrimaryKeySelective(ResourceType record);

    int updateByPrimaryKey(ResourceType record);

    ResourceType selectByResourceName(String resourceName);

    List<ResourceTypeVo> selectAllRows(ResourceTypeVo resourceTypeVo);
}