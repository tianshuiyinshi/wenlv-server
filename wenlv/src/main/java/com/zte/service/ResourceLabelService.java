package com.zte.service;

import com.zte.bean.ResourceLabel;
import com.zte.bean.vo.ResourceLabelVo;
import com.zte.dao.ResourceLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-28 16:11
 */
@Service
public class ResourceLabelService {

    @Autowired
    ResourceLabelMapper resourceLabelMapper;

    public Integer addResourceLabel(ResourceLabel resourceLabel){
        return resourceLabelMapper.insertSelective(resourceLabel);
    }

    public Integer deleteResourceLabel(Integer mapId){
        return resourceLabelMapper.deleteByPrimaryKey(mapId);
    }

    public Integer updateResourceLabel(ResourceLabel resourceLabel){
        return resourceLabelMapper.updateByPrimaryKeySelective(resourceLabel);
    }

    public List<ResourceLabelVo> queryAllRows(ResourceLabelVo resourceLabelVo){
        return resourceLabelMapper.selectAllRows(resourceLabelVo);
    }

    public ResourceLabel queryResourceLabelById(Integer mapId){
        return resourceLabelMapper.selectByPrimaryKey(mapId);
    }

    public Integer deleteResourceLabelByLabelId(Integer sysLabelId) {
        return resourceLabelMapper.deleteByLabelId(sysLabelId);
    }
}
