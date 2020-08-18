package com.zte.dao;

import java.util.List;

import com.zte.bean.ResourceSite;
import com.zte.bean.dto.AuditDto;
import com.zte.bean.vo.ResourceSiteVo;
import io.swagger.models.auth.In;


public interface ResourceSiteMapper {
    int deleteByPrimaryKey(Integer resourceid);

    int insert(ResourceSite record);

    Integer insertSelective(ResourceSiteVo record);

    ResourceSiteVo selectByPrimaryKey(Integer resourceid);

    int updateByPrimaryKeySelective(ResourceSite record);

    int updateByPrimaryKeyWithBLOBs(ResourceSite record);

    int updateByPrimaryKey(ResourceSite record);
    
    List<ResourceSiteVo> selectAll(ResourceSiteVo record);

    Integer updateByPrimaryKeysSelective(AuditDto auditDto);
}