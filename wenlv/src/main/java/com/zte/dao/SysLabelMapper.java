package com.zte.dao;

import com.zte.bean.SysLabel;

import java.util.List;

public interface SysLabelMapper {
    int deleteByPrimaryKey(Integer labelid);

    int insert(SysLabel record);

    int insertSelective(SysLabel record);

    SysLabel selectByPrimaryKey(Integer labelid);

    int updateByPrimaryKeySelective(SysLabel record);

    int updateByPrimaryKey(SysLabel record);

    List<SysLabel> selectAllRows(SysLabel sysLabel);


    SysLabel selectByName(String labelword);
}