package com.zte.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "活动资源基础类")
public class ResourceActivity implements Serializable {
    private Integer resourceid;

    private String resourcetitle;

    private Integer resourcetypeid;

    private String resourceimage;

    private String resourceipicture;

    private String resourceviedo;

    private String address;

    private String longitude;

    private String latitude;

    private String resourceboards;

    private String resourcelink;

    @ApiModelProperty(value = "活动开始时间")
    private String resourcestarttime;
    @ApiModelProperty(value = "活动结束时间")
    private String resourceendtime;
    @ApiModelProperty(value = "预约开始时间")
    private String bespeakstarttime;
    @ApiModelProperty(value = "预约结束时间")
    private String bespeakendtime;
    @ApiModelProperty(value = "主办单位")
    private String hostunit;
    @ApiModelProperty(value = "最大人数")
    private Integer maxamount;

    private Integer seqno;

    private Integer upflag;

    private Integer status;

    private Integer creator;

    private String createtime;

    private Integer updater;

    private String updatetime;

    private Integer auditor;

    private String audittime;

    private String auditdesc;

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

    public String getResourcestarttime() {
        return resourcestarttime;
    }

    public void setResourcestarttime(String resourcestarttime) {
        this.resourcestarttime = resourcestarttime == null ? null : resourcestarttime.trim();
    }

    public String getResourceendtime() {
        return resourceendtime;
    }

    public void setResourceendtime(String resourceendtime) {
        this.resourceendtime = resourceendtime == null ? null : resourceendtime.trim();
    }

    public String getBespeakstarttime() {
        return bespeakstarttime;
    }

    public void setBespeakstarttime(String bespeakstarttime) {
        this.bespeakstarttime = bespeakstarttime == null ? null : bespeakstarttime.trim();
    }

    public String getBespeakendtime() {
        return bespeakendtime;
    }

    public void setBespeakendtime(String bespeakendtime) {
        this.bespeakendtime = bespeakendtime == null ? null : bespeakendtime.trim();
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
        sb.append(", address=").append(address);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", resourceboards=").append(resourceboards);
        sb.append(", resourcelink=").append(resourcelink);
        sb.append(", resourcestarttime=").append(resourcestarttime);
        sb.append(", resourceendtime=").append(resourceendtime);
        sb.append(", bespeakstarttime=").append(bespeakstarttime);
        sb.append(", bespeakendtime=").append(bespeakendtime);
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