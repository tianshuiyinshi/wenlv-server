package com.zte.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonResult {

    public static JsonResult ERR_SYS = new JsonResult("9999", "9999", "系统忙，请稍后再试");

    public static JsonResult SUCC = new JsonResult("0000", "0000", "success");

    private static Logger log = LoggerFactory.getLogger(JsonResult.class);

    private String code;

    private String innercode;

    private Object message;

    public JsonResult() {
        super();
    }

    public JsonResult(String code, String innercode, Object message) {
        super();
        this.code = code;
        this.innercode = innercode;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getInnercode() {
        return innercode;
    }

    public void setInnercode(String innercode) {
        this.innercode = innercode;
    }

    /**
     * 成功返回
     * 
     * @param msg
     * @return
     */
    public static JsonResult getSuccess(Object msg) {
        JsonResult json = new JsonResult();
        json.setCode("0000");
        json.setInnercode("0000");
        json.setMessage(msg);
        return json;
    }

    /**
     * 业务错误返回
     * 
     * @param msg
     * @return
     */
    public static JsonResult getInnerFail(String innercode, String msg) {
        log.error("返回错误信息innercode：" + innercode + "msg:" + msg);
        JsonResult json = new JsonResult();
        json.setCode("0000");
        json.setInnercode(innercode);
        json.setMessage(msg);
        return json;
    }

    /**
         * 系统错误返回
     * 
     * @param msg
     * @return
     */
    public static JsonResult getFail(String innercode,Object msg) {
        JsonResult json = new JsonResult();
        json.setCode("9999");
        json.setInnercode(innercode);
        json.setMessage(msg);
        return json;
    }
    /**
     * 系统错误返回
     * 
     * @param msg
     * @return
     */
    public static JsonResult getFail(String msg) {
        log.error("返回错误信息" + msg);
        JsonResult json = new JsonResult();
        json.setCode("9999");
        json.setInnercode("9999");
        json.setMessage(msg);
        return json;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "code='" + code + '\'' +
                ", innercode='" + innercode + '\'' +
                ", message=" + message +
                '}';
    }
}