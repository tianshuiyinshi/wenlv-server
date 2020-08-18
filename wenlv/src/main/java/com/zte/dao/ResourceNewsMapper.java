package com.zte.dao;



import java.util.List;

import com.zte.bean.ResourceNews;
import com.zte.bean.dto.AuditDto;
import com.zte.bean.vo.ResourceNewsVo;

public interface ResourceNewsMapper {

	int deleteByPrimaryKey(Integer resourceid);

    int insert(ResourceNews record);

    Integer insertSelective(ResourceNewsVo record);

    ResourceNewsVo selectByPrimaryKey(Integer resourceid);

    int updateByPrimaryKeySelective(ResourceNews record);

    int updateByPrimaryKeyWithBLOBs(ResourceNews record);

    int updateByPrimaryKey(ResourceNews record);
    
    List<ResourceNewsVo> selectAll(ResourceNewsVo record);

    Integer updateByPrimaryKeysSelective(AuditDto auditDto);
}