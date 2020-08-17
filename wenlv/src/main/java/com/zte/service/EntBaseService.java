package com.zte.service;

import com.zte.bean.EntBase;
import com.zte.dao.EntBaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-22 09:50
 */
@Service
public class EntBaseService {

    @Autowired
    EntBaseMapper entBaseMapper;

    /**
     * @author yinsiwei
     * @date 2020-07-22 09:58
     * 查询机构列表
    */
    public List<EntBase> queryAllEntBase(){
        return entBaseMapper.selectAllRows();
    }

    /**
     * @author yinsiwei
     * @date 2020-07-22 10:30
     * 根据机构名称查询机构
    */
    public EntBase queryEntBaseByFullName(String entBaseFullName){
        return entBaseMapper.selectByEntBaseFullName(entBaseFullName);
    }

    /**
     * @author yinsiwei
     * @date 2020-07-22 14:22
     * 根据机构Id查询机构
    */
    public EntBase queryEntBaseById(String entId){
        return entBaseMapper.selectByPrimaryKey(Integer.parseInt(entId));
    }

    /**
     * @author yinsiwei
     * @date 2020-07-22 09:58
     * 机构添加
    */
    public void addEntBase(EntBase entBase){
        entBaseMapper.insertSelective(entBase);
    }

    /**
     * @author yinsiwei
     * @date 2020-07-22 10:02
     * 机构修改
    */
    public void alterEntBase(EntBase entBase){
        entBaseMapper.updateByPrimaryKeySelective(entBase);
    }

    /**
     * @author yinsiwei
     * @date 2020-07-22 10:02
     * 机构删除
    */
    public void delEntBase(String entId){
        entBaseMapper.deleteByPrimaryKey(Integer.parseInt(entId));
    }
}
