package com.zte.dao;

import com.zte.bean.AdminRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

    List<AdminRole> selectByAdminId(Integer adminId);

    List<AdminRole> selectByRoleId(Integer roleId);

    AdminRole selectByAdminIdAndRoleId(@Param("adminId") Integer adminId, @Param("roleId") Integer roleId);

    int deleteByAdminIdAndRoleId(@Param("adminId") Integer adminId, @Param("roleId") Integer roleId);
}