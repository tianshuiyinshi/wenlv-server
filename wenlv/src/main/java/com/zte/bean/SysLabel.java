package com.zte.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "系统标签基础类")
public class SysLabel implements Serializable {
    @ApiModelProperty(value = "系统标签id")
    private Integer labelid;
    @ApiModelProperty(value = "系统标签名称")
    private String labelword;
    @ApiModelProperty(value = "系统标签描述")
    private String labeldesc;
    @ApiModelProperty(value = "系统标签状态")
    private Integer status;
    @ApiModelProperty(value = "系统标签创建者")
    private Integer creator;
    @ApiModelProperty(value = "系统标签创建时间")
    private String createtime;
    @ApiModelProperty(value = "系统标签更新者")
    private Integer updater;
    @ApiModelProperty(value = "系统标签更新时间")
    private String updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getLabelid() {
        return labelid;
    }

    public void setLabelid(Integer labelid) {
        this.labelid = labelid;
    }

    public String getLabelword() {
        return labelword;
    }

    public void setLabelword(String labelword) {
        this.labelword = labelword == null ? null : labelword.trim();
    }

    public String getLabeldesc() {
        return labeldesc;
    }

    public void setLabeldesc(String labeldesc) {
        this.labeldesc = labeldesc == null ? null : labeldesc.trim();
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
        sb.append(", labelid=").append(labelid);
        sb.append(", labelword=").append(labelword);
        sb.append(", labeldesc=").append(labeldesc);
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