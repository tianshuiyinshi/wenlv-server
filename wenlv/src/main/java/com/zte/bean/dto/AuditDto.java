package com.zte.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-08-15 16:54
 */
@ApiModel(description = "审批消息类")
public class AuditDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "审批人(自动获取)")
    private Integer auditor;

    @ApiModelProperty(value = "审批时间(自动获取)")
    private String audittime;

    @ApiModelProperty(value = "审批意见")
    private String auditdesc;

    @ApiModelProperty(value = "资源id列表")
    private List<Integer> resourceIds;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        this.audittime = audittime;
    }

    public String getAuditdesc() {
        return auditdesc;
    }

    public void setAuditdesc(String auditdesc) {
        this.auditdesc = auditdesc;
    }

    public List<Integer> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Integer> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
