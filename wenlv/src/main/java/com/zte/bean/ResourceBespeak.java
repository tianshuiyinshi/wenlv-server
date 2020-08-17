package com.zte.bean;

import java.io.Serializable;

public class ResourceBespeak implements Serializable {
    private Integer bespeakid;

    private Integer userid;

    private Integer resourceid;

    private Integer resourcetoptypeid;

    private Integer resourcetypeid;

    private String bespeakday;

    private String bespeaklinkman;

    private String bespeaklinkmobile;

    private Integer bespeakamount;

    private Integer adultamount;

    private Integer minoramount;

    private Integer tableid;

    private Integer status;

    private String createtime;

    private String renouncetime;

    private static final long serialVersionUID = 1L;

    public Integer getBespeakid() {
        return bespeakid;
    }

    public void setBespeakid(Integer bespeakid) {
        this.bespeakid = bespeakid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public Integer getResourcetoptypeid() {
        return resourcetoptypeid;
    }

    public void setResourcetoptypeid(Integer resourcetoptypeid) {
        this.resourcetoptypeid = resourcetoptypeid;
    }

    public Integer getResourcetypeid() {
        return resourcetypeid;
    }

    public void setResourcetypeid(Integer resourcetypeid) {
        this.resourcetypeid = resourcetypeid;
    }

    public String getBespeakday() {
        return bespeakday;
    }

    public void setBespeakday(String bespeakday) {
        this.bespeakday = bespeakday == null ? null : bespeakday.trim();
    }

    public String getBespeaklinkman() {
        return bespeaklinkman;
    }

    public void setBespeaklinkman(String bespeaklinkman) {
        this.bespeaklinkman = bespeaklinkman == null ? null : bespeaklinkman.trim();
    }

    public String getBespeaklinkmobile() {
        return bespeaklinkmobile;
    }

    public void setBespeaklinkmobile(String bespeaklinkmobile) {
        this.bespeaklinkmobile = bespeaklinkmobile == null ? null : bespeaklinkmobile.trim();
    }

    public Integer getBespeakamount() {
        return bespeakamount;
    }

    public void setBespeakamount(Integer bespeakamount) {
        this.bespeakamount = bespeakamount;
    }

    public Integer getAdultamount() {
        return adultamount;
    }

    public void setAdultamount(Integer adultamount) {
        this.adultamount = adultamount;
    }

    public Integer getMinoramount() {
        return minoramount;
    }

    public void setMinoramount(Integer minoramount) {
        this.minoramount = minoramount;
    }

    public Integer getTableid() {
        return tableid;
    }

    public void setTableid(Integer tableid) {
        this.tableid = tableid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getRenouncetime() {
        return renouncetime;
    }

    public void setRenouncetime(String renouncetime) {
        this.renouncetime = renouncetime == null ? null : renouncetime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bespeakid=").append(bespeakid);
        sb.append(", userid=").append(userid);
        sb.append(", resourceid=").append(resourceid);
        sb.append(", resourcetoptypeid=").append(resourcetoptypeid);
        sb.append(", resourcetypeid=").append(resourcetypeid);
        sb.append(", bespeakday=").append(bespeakday);
        sb.append(", bespeaklinkman=").append(bespeaklinkman);
        sb.append(", bespeaklinkmobile=").append(bespeaklinkmobile);
        sb.append(", bespeakamount=").append(bespeakamount);
        sb.append(", adultamount=").append(adultamount);
        sb.append(", minoramount=").append(minoramount);
        sb.append(", tableid=").append(tableid);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append(", renouncetime=").append(renouncetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}