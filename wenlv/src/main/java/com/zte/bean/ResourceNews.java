package com.zte.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "资讯资源基础类")
public class ResourceNews implements Serializable {
    private Integer resourceid;


    private String resourcetitle;

    private Integer resourcetypeid;

    private String resourceimage;

    private String resourceipicture;

    private String resourceviedo;

    private String resourcesource;

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

    public String getResourcesource() {
        return resourcesource;
    }

    public void setResourcesource(String resourcesource) {
        this.resourcesource = resourcesource == null ? null : resourcesource.trim();
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
        sb.append(", resourcesource=").append(resourcesource);
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