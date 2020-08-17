package com.zte.dao;

import com.zte.bean.ResourceCollect;
import com.zte.bean.vo.ResourceCollectVo;

import java.util.List;

public interface ResourceCollectMapper {
    int deleteByPrimaryKey(Integer collectid);

    int insert(ResourceCollect record);

    int insertSelective(ResourceCollectVo record);

    ResourceCollect selectByPrimaryKey(Integer collectid);

    int updateByPrimaryKeySelective(ResourceCollect record);

    int updateByPrimaryKey(ResourceCollect record);

    List<ResourceCollectVo> selectAllRows(ResourceCollectVo resourceCollectVo);
}