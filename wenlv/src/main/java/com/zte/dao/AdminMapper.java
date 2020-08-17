package com.zte.dao;

import com.zte.bean.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer adminId);

    List<Admin> selectAll(Admin admin);

    Admin selectByAccount(String account);

    Admin selectByAccountAndPassword(@Param("account") String account,@Param("password") String password);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}