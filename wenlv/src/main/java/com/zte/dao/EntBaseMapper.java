package com.zte.dao;

import com.zte.bean.EntBase;

import java.util.List;

public interface EntBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EntBase record);

    int insertSelective(EntBase record);

    EntBase selectByPrimaryKey(Integer id);

    EntBase selectByEntBaseFullName(String EntBaseName);

    List<EntBase> selectAllRows();

    int updateByPrimaryKeySelective(EntBase record);

    int updateByPrimaryKey(EntBase record);

}