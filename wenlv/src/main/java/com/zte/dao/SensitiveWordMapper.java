package com.zte.dao;

import com.zte.bean.SensitiveWord;

public interface SensitiveWordMapper {
    int deleteByPrimaryKey(Integer wordid);

    int insert(SensitiveWord record);

    int insertSelective(SensitiveWord record);

    SensitiveWord selectByPrimaryKey(Integer wordid);

    int updateByPrimaryKeySelective(SensitiveWord record);

    int updateByPrimaryKey(SensitiveWord record);
}