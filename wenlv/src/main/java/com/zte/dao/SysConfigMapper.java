package com.zte.dao;

import java.util.List;

import com.zte.bean.SysConfig;

public interface SysConfigMapper {
    int deleteByPrimaryKey(Integer configid);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(Integer configid);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);
    
    SysConfig selectByCfgkey(String cfgkey);
    
    List<SysConfig> selectAll();
}