package com.zte.bean;

import java.io.Serializable;

public class SysConfig implements Serializable {
    private Integer configid;

    private String configname;

    private String configkey;

    private String configvalue;

    private String configdesc;

    private Short status;

    private Integer creator;

    private String createtime;

    private Integer updater;

    private String updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getConfigid() {
        return configid;
    }

    public void setConfigid(Integer configid) {
        this.configid = configid;
    }

    public String getConfigname() {
        return configname;
    }

    public void setConfigname(String configname) {
        this.configname = configname == null ? null : configname.trim();
    }

    public String getConfigkey() {
        return configkey;
    }

    public void setConfigkey(String configkey) {
        this.configkey = configkey == null ? null : configkey.trim();
    }

    public String getConfigvalue() {
        return configvalue;
    }

    public void setConfigvalue(String configvalue) {
        this.configvalue = configvalue == null ? null : configvalue.trim();
    }

    public String getConfigdesc() {
        return configdesc;
    }

    public void setConfigdesc(String configdesc) {
        this.configdesc = configdesc == null ? null : configdesc.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
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
        sb.append(", configid=").append(configid);
        sb.append(", configname=").append(configname);
        sb.append(", configkey=").append(configkey);
        sb.append(", configvalue=").append(configvalue);
        sb.append(", configdesc=").append(configdesc);
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