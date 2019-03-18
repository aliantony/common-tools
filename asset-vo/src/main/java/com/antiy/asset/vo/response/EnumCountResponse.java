package com.antiy.asset.vo.response;

/**
 * 统计返回
 */

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举统计返回
 */
public class EnumCountResponse {
    @ApiModelProperty("枚举名，用于展示")
    private String msg;
    @ApiModelProperty("用于做查询的参数")
    private List<String> code;
    @ApiModelProperty("统计的数量")
    private long number;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public EnumCountResponse() {
    }

    public EnumCountResponse(String msg, List<String> code, long number) {
        this.msg = msg;
        this.code = code;
        this.number = number;
    }

    public EnumCountResponse(String msg, String code, long number) {
        List<String> codeList = new ArrayList<>();
        codeList.add(code);
        this.msg = msg;
        this.code = codeList;
        this.number = number;
    }

    @Override
    public String toString() {
        return "EnumCountResponse{" + "msg='" + msg + '\'' + ", code='" + code + '\'' + ", number=" + number + '}';
    }
}