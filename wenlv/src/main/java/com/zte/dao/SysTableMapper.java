package com.zte.dao;

import com.zte.bean.SysTable;

public interface SysTableMapper {
    int deleteByPrimaryKey(Integer tableid);

    int insert(SysTable record);

    int insertSelective(SysTable record);

    SysTable selectByPrimaryKey(Integer tableid);

    int updateByPrimaryKeySelective(SysTable record);

    int updateByPrimaryKey(SysTable record);
}