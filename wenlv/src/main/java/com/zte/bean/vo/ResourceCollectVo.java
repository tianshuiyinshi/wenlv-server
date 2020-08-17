package com.zte.bean.vo;

import com.zte.bean.ResourceCollect;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-08-12 14:38
 */
public class ResourceCollectVo extends ResourceCollect {

    private String startTime;
    private String endTime;

    private ResourceActivityVo resourceActivityVo;
    private ResourceNewsVo resourceNewsVo;
    private ResourceSiteVo resourceSiteVo;

    private static final long serialVersionUID = 1L;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ResourceActivityVo getResourceActivityVo() {
        return resourceActivityVo;
    }

    public void setResourceActivityVo(ResourceActivityVo resourceActivityVo) {
        this.resourceActivityVo = resourceActivityVo;
    }

    public ResourceNewsVo getResourceNewsVo() {
        return resourceNewsVo;
    }

    public void setResourceNewsVo(ResourceNewsVo resourceNewsVo) {
        this.resourceNewsVo = resourceNewsVo;
    }

    public ResourceSiteVo getResourceSiteVo() {
        return resourceSiteVo;
    }

    public void setResourceSiteVo(ResourceSiteVo resourceSiteVo) {
        this.resourceSiteVo = resourceSiteVo;
    }
}
