package com.zte.service;

import com.zte.bean.SysLabel;
import com.zte.common.utils.DateUtil;
import com.zte.dao.SysLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-28 15:52
 */
@Service
public class SysLabelService {

    @Autowired
    SysLabelMapper sysLabelMapper;

    public Integer addSysLabel(SysLabel sysLabel){
        sysLabel.setStatus(1);
        String currentTime = DateUtil.getDBDatetime();
        sysLabel.setCreatetime(currentTime);
        sysLabel.setUpdatetime(currentTime);
        return sysLabelMapper.insertSelective(sysLabel);
    }

    public Integer updateSysLabel(SysLabel sysLabel){
        return sysLabelMapper.updateByPrimaryKeySelective(sysLabel);
    }

    public Integer deleteSysLabel(Integer sysLabelId){
        return sysLabelMapper.deleteByPrimaryKey(sysLabelId);
    }

    public List<SysLabel> queryAllRows(SysLabel sysLabel){
        return sysLabelMapper.selectAllRows(sysLabel);
    }

    public SysLabel querySysLabelById(Integer sysLabelId){
        return sysLabelMapper.selectByPrimaryKey(sysLabelId);
    }

    public SysLabel querySysLabelByName(String labelword) {
        return sysLabelMapper.selectByName(labelword);
    }
}
