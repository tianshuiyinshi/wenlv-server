package com.zte.service;

import com.zte.bean.ResourceBespeak;
import com.zte.bean.SysLabel;
import com.zte.bean.vo.ResourceBespeakVo;
import com.zte.common.utils.DateUtil;
import com.zte.dao.ResourceBespeakMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-31 09:52
 */
@Service
public class ResourceBespeakService {
    @Autowired
    ResourceBespeakMapper resourceBespeakMapper;

    public boolean insertResourceBespeak(ResourceBespeak resourceBespeak){
        String dbDatetime = DateUtil.getDBDatetime();
        resourceBespeak.setCreatetime(dbDatetime);
        int i = resourceBespeakMapper.insertSelective(resourceBespeak);
        return i==1?true:false;
    }

    public boolean updateResourceBespeak(ResourceBespeak resourceBespeak){
        String dbDatetime = DateUtil.getDBDatetime();
        resourceBespeak.setRenouncetime(dbDatetime);
        int i = resourceBespeakMapper.updateByPrimaryKeySelective(resourceBespeak);
        return i==1?true:false;
    }

    public List<ResourceBespeakVo>  queryAllRows(ResourceBespeakVo resourceBespeakVo){
        return resourceBespeakMapper.selectAllRows(resourceBespeakVo);
    }


}
