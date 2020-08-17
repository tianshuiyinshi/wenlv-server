package com.zte.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "场馆资源基础类")
public class ResourceSite implements Serializable {
    @ApiModelProperty(value = "资源id")
    private Integer resourceid;
    @ApiModelProperty(value = "场馆名称")
    private String resourcetitle;
    @ApiModelProperty(value = "资源类型id")
    private Integer resourcetypeid;
    @ApiModelProperty(value = "资源图")
    private String resourceimage;
    @ApiModelProperty(value = "资源高清图")
    private String resourceipicture;
    @ApiModelProperty(value = "资源视频")
    private String resourceviedo;
    @ApiModelProperty(value = "资源公告")
    private String resourceboards;
    @ApiModelProperty(value = "联系方式")
    private String resourcelink;
    @ApiModelProperty(value = "开放时间类型")
    private Integer opentype;
    @ApiModelProperty(value = "开放日期")
    private String openday;
    @ApiModelProperty(value = "开放时间段")
    private String opentime;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "主管单位")
    private String hostunit;
    @ApiModelProperty(value = "最大容量")
    private Integer maxamount;
    @ApiModelProperty(value = "排序号")
    private Integer seqno;
    @ApiModelProperty(value = "置顶标志")
    private Integer upflag;
    @ApiModelProperty(value = "资源状态")
    private Integer status;
    @ApiModelProperty(value = "创建者id")
    private Integer creator;
    @ApiModelProperty(value = "创建时间")
    private String createtime;
    @ApiModelProperty(value = "更新者id")
    private Integer updater;
    @ApiModelProperty(value = "更新时间")
    private String updatetime;
    @ApiModelProperty(value = "审批者id")
    private Integer auditor;
    @ApiModelProperty(value = "审批时间")
    private String audittime;
    @ApiModelProperty(value = "审批详情")
    private String auditdesc;
    @ApiModelProperty(value = "资源描述")
    private String resourcedetail;

    private static final long serialVersionUID = 1L;

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcetitle() {
        return resourcetitle;
    }

    public void setResourcetitle(String resourcetitle) {
        this.resourcetitle = resourcetitle == null ? null : resourcetitle.trim();
    }

    public Integer getResourcetypeid() {
        return resourcetypeid;
    }

    public void setResourcetypeid(Integer resourcetypeid) {
        this.resourcetypeid = resourcetypeid;
    }

    public String getResourceimage() {
        return resourceimage;
    }

    public void setResourceimage(String resourceimage) {
        this.resourceimage = resourceimage == null ? null : resourceimage.trim();
    }

    public String getResourceipicture() {
        return resourceipicture;
    }

    public void setResourceipicture(String resourceipicture) {
        this.resourceipicture = resourceipicture == null ? null : resourceipicture.trim();
    }

    public String getResourceviedo() {
        return resourceviedo;
    }

    public void setResourceviedo(String resourceviedo) {
        this.resourceviedo = resourceviedo == null ? null : resourceviedo.trim();
    }

    public String getResourceboards() {
        return resourceboards;
    }

    public void setResourceboards(String resourceboards) {
        this.resourceboards = resourceboards == null ? null : resourceboards.trim();
    }

    public String getResourcelink() {
        return resourcelink;
    }

    public void setResourcelink(String resourcelink) {
        this.resourcelink = resourcelink == null ? null : resourcelink.trim();
    }

    public Integer getOpentype() {
        return opentype;
    }

    public void setOpentype(Integer opentype) {
        this.opentype = opentype;
    }

    public String getOpenday() {
        return openday;
    }

    public void setOpenday(String openday) {
        this.openday = openday == null ? null : openday.trim();
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime == null ? null : opentime.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getHostunit() {
        return hostunit;
    }

    public void setHostunit(String hostunit) {
        this.hostunit = hostunit == null ? null : hostunit.trim();
    }

    public Integer getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(Integer maxamount) {
        this.maxamount = maxamount;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Integer getUpflag() {
        return upflag;
    }

    public void setUpflag(Integer upflag) {
        this.upflag = upflag;
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

    public Integer getAuditor() {
        return auditor;
    }

    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
    }

    public String getAudittime() {
        return audittime;
    }

    public void setAudittime(String audittime) {
        this.audittime = audittime == null ? null : audittime.trim();
    }

    public String getAuditdesc() {
        return auditdesc;
    }

    public void setAuditdesc(String auditdesc) {
        this.auditdesc = auditdesc == null ? null : auditdesc.trim();
    }

    public String getResourcedetail() {
        return resourcedetail;
    }

    public void setResourcedetail(String resourcedetail) {
        this.resourcedetail = resourcedetail == null ? null : resourcedetail.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", resourceid=").append(resourceid);
        sb.append(", resourcetitle=").append(resourcetitle);
        sb.append(", resourcetypeid=").append(resourcetypeid);
        sb.append(", resourceimage=").append(resourceimage);
        sb.append(", resourceipicture=").append(resourceipicture);
        sb.append(", resourceviedo=").append(resourceviedo);
        sb.append(", resourceboards=").append(resourceboards);
        sb.append(", resourcelink=").append(resourcelink);
        sb.append(", opentype=").append(opentype);
        sb.append(", openday=").append(openday);
        sb.append(", opentime=").append(opentime);
        sb.append(", address=").append(address);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", hostunit=").append(hostunit);
        sb.append(", maxamount=").append(maxamount);
        sb.append(", seqno=").append(seqno);
        sb.append(", upflag=").append(upflag);
        sb.append(", status=").append(status);
        sb.append(", creator=").append(creator);
        sb.append(", createtime=").append(createtime);
        sb.append(", updater=").append(updater);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", auditor=").append(auditor);
        sb.append(", audittime=").append(audittime);
        sb.append(", auditdesc=").append(auditdesc);
        sb.append(", resourcedetail=").append(resourcedetail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}