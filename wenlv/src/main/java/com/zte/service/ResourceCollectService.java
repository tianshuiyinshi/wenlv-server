package com.zte.service;

import com.zte.bean.ResourceCollect;
import com.zte.bean.vo.ResourceCollectVo;
import com.zte.dao.ResourceCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-08-05 15:23
 */
@Service
public class ResourceCollectService {

    @Autowired
    ResourceCollectMapper resourceCollectMapper;

    public Boolean addResourceCollect(ResourceCollectVo resourceCollectVo){
        return resourceCollectMapper.insertSelective(resourceCollectVo)==1?true:false;
    }

    public Boolean deleteResourceCollect(Integer resourceCollectId){
        return resourceCollectMapper.deleteByPrimaryKey(resourceCollectId)==1?true:false;
    }

    public List<ResourceCollectVo> queryAllRows(ResourceCollectVo resourceCollectVo){
        return resourceCollectMapper.selectAllRows(resourceCollectVo);
    }
}
