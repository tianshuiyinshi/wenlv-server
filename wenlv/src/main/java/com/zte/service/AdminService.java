package com.zte.service;

import com.zte.bean.Admin;
import com.zte.dao.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @comment
 * @Author zhongyong 2020/7/16 9:06
 */


@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminMapper adminMapper;

    public Admin queryAdminById(Integer adminId){
        return adminMapper.selectByPrimaryKey(adminId);
    }

    public Admin login(String account, String password) {
        Admin admin = adminMapper.selectByAccountAndPassword(account,password);
        return admin;
    }

    public Integer register(Admin admin){
        return adminMapper.insertSelective(admin);
    }

    public Admin queryAdminByAccount(String account){
        return adminMapper.selectByAccount(account);
    }

    public List<Admin> queryAll(Admin admin){return adminMapper.selectAll(admin);}


    public Integer updateAdmin(Admin admin){
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public Integer delAdmin(Admin admin){
        return adminMapper.updateByPrimaryKeySelective(admin);
    }


}
