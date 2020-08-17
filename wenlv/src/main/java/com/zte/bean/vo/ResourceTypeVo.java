package com.zte.bean.vo;

import com.zte.bean.ResourceType;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-08-11 15:49
 */
public class ResourceTypeVo extends ResourceType {
    private static final long serialVersionUID = 1L;

    private String upResourceTypeName;
    private List<ResourceTypeVo> resourceTypeVos;

    public String getUpResourceTypeName() {
        return upResourceTypeName;
    }

    public void setUpResourceTypeName(String upResourceTypeName) {
        this.upResourceTypeName = upResourceTypeName;
    }

    public List<ResourceTypeVo> getResourceTypeVos() {
        return resourceTypeVos;
    }

    public void setResourceTypeVos(List<ResourceTypeVo> resourceTypeVos) {
        this.resourceTypeVos = resourceTypeVos;
    }
}
