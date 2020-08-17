package com.zte.bean.vo;

/**
 * @comment
 * @Author zhongyong 2020/7/13 15:22
 */
public class BaseVo {
    private Integer resultStatus;  // 0.成功，1.参数异常，2.数据不存在
    private String resultMsg;  // 0.成功，1.参数异常，2.数据不存在

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
