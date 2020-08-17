package com.zte.bean.vo;

import com.zte.bean.ResourceActivity;
import com.zte.bean.ResourceBespeak;
import com.zte.bean.ResourceSite;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yinsiwei
 * @date 2020-08-11 11:09
 */
@ApiModel(description = "预约消息类")
public class ResourceBespeakVo extends ResourceBespeak {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询起始时间")
    private String startTime;
    @ApiModelProperty(value = "查询结束时间")
    private String endTime;

    private ResourceSiteVo resourceSiteVo;
    private ResourceActivityVo resourceActivityVo;

    public ResourceSiteVo getResourceSiteVo() {
        return resourceSiteVo;
    }

    public void setResourceSiteVo(ResourceSiteVo resourceSiteVo) {
        this.resourceSiteVo = resourceSiteVo;
    }

    public ResourceActivityVo getResourceActivityVo() {
        return resourceActivityVo;
    }

    public void setResourceActivityVo(ResourceActivityVo resourceActivityVo) {
        this.resourceActivityVo = resourceActivityVo;
    }

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
}
