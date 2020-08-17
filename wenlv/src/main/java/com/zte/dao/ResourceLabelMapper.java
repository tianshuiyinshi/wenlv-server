package com.zte.dao;

import com.zte.bean.ResourceLabel;
import com.zte.bean.vo.ResourceLabelVo;

import java.util.List;

public interface ResourceLabelMapper {
    int deleteByPrimaryKey(Integer mapid);

    int insert(ResourceLabel record);

    int insertSelective(ResourceLabel record);

    ResourceLabel selectByPrimaryKey(Integer mapid);

    int updateByPrimaryKeySelective(ResourceLabel record);

    int updateByPrimaryKey(ResourceLabel record);

    List<ResourceLabelVo> selectAllRows(ResourceLabelVo resourceLabelVo);

    Integer deleteByLabelId(Integer sysLabelId);
}