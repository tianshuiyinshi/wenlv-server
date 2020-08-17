package com.zte.dao;

import com.zte.bean.ResourceBespeak;
import com.zte.bean.SysLabel;
import com.zte.bean.vo.ResourceBespeakVo;

import java.util.List;

public interface ResourceBespeakMapper {
    int deleteByPrimaryKey(Integer bespeakid);

    int insert(ResourceBespeak record);

    int insertSelective(ResourceBespeak record);

    ResourceBespeak selectByPrimaryKey(Integer bespeakid);

    int updateByPrimaryKeySelective(ResourceBespeak record);

    int updateByPrimaryKey(ResourceBespeak record);

    List<ResourceBespeakVo> selectAllRows(ResourceBespeakVo resourceBespeakVo);
}