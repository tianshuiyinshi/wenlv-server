package com.zte.bean.vo;

import com.zte.bean.ResourceLabel;
import com.zte.bean.SysLabel;


/**
 * @author yinsiwei
 * @date 2020-08-14 21:18
 */
public class ResourceLabelVo extends ResourceLabel {
    private static final long serialVersionUID = 1L;

    private SysLabel sysLabel;

    public SysLabel getSysLabel() {
        return sysLabel;
    }

    public void setSysLabel(SysLabel sysLabel) {
        this.sysLabel = sysLabel;
    }
}
