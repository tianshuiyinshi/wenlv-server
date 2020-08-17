package com.zte.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "资源类型基础类")
public class ResourceType implements Serializable {
    @ApiModelProperty(value = "资源类型id")
    private Integer resourcetypeid;

    @ApiModelProperty(value = "资源类型名称")
    private String resourtypename;
    @ApiModelProperty(value = "上级资源类型名称")
    private Integer upresourcetypeid;
    @ApiModelProperty(value = "资源类型状态")
    private Integer status;
    @ApiModelProperty(value = "资源类型创建者id")
    private Integer creator;
    @ApiModelProperty(value = "资源类型创建时间")
    private String createtime;
    @ApiModelProperty(value = "资源类型更新者")
    private Integer updater;
    @ApiModelProperty(value = "资源类型更新时间")
    private String updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getResourcetypeid() {
        return resourcetypeid;
    }

    public void setResourcetypeid(Integer resourcetypeid) {
        this.resourcetypeid = resourcetypeid;
    }

    public String getResourtypename() {
        return resourtypename;
    }

    public void setResourtypename(String resourtypename) {
        this.resourtypename = resourtypename == null ? null : resourtypename.trim();
    }

    public Integer getUpresourcetypeid() {
        return upresourcetypeid;
    }

    public void setUpresourcetypeid(Integer upresourcetypeid) {
        this.upresourcetypeid = upresourcetypeid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", resourcetypeid=").append(resourcetypeid);
        sb.append(", resourtypename=").append(resourtypename);
        sb.append(", upresourcetypeid=").append(upresourcetypeid);
        sb.append(", status=").append(status);
        sb.append(", creator=").append(creator);
        sb.append(", createtime=").append(createtime);
        sb.append(", updater=").append(updater);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}