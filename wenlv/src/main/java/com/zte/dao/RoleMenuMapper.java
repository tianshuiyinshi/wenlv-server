package com.zte.dao;

import com.zte.bean.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByRoleId(Integer roleId);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Integer id);

    List<RoleMenu> selectByRoleId(Integer id);

    List<RoleMenu> selectByMenuId(Integer id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);
}