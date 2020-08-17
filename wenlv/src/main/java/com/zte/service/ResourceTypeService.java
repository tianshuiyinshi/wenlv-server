package com.zte.service;

import com.zte.bean.ResourceType;
import com.zte.bean.vo.ResourceTypeVo;
import com.zte.common.utils.DateUtil;
import com.zte.dao.ResourceTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-27 17:20
 */
@Service
public class ResourceTypeService {
    @Autowired
    ResourceTypeMapper resourceTypeMapper;

    public List<ResourceTypeVo> queryAllRows(ResourceTypeVo resourceTypeVo){
        return resourceTypeMapper.selectAllRows(resourceTypeVo);
    }

    public ResourceType queryResourceTypeByName(String resourceName){
        return resourceTypeMapper.selectByResourceName(resourceName);
    }

    public ResourceType queryResourceTypeById(Integer resourceTypeId){
        return resourceTypeMapper.selectByPrimaryKey(resourceTypeId);
    }

    public Integer updateResourceType(ResourceType resourceType){
        return resourceTypeMapper.updateByPrimaryKeySelective(resourceType);
    }

    public Integer addResourceType(ResourceType resourceType){
        //添加创建时间和更新时间
        String currentTime = DateUtil.getDBDatetime();
        resourceType.setCreatetime(currentTime);
        resourceType.setUpdatetime(currentTime);
        return resourceTypeMapper.insertSelective(resourceType);
    }
}
