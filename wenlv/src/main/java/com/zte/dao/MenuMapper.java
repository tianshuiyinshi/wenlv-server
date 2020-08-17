package com.zte.dao;

import com.zte.bean.Menu;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    Menu selectByMenuUrl(String menuUrl);

    List<Menu> selectAllRows();

    List<Menu> selectByMenuIds(List<Integer> menuIds);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}