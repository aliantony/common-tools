package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/5/20
 */
public class EntrySystemResponse {
    @ApiModelProperty("token密钥")
    private String token;
    @ApiModelProperty("错误信息")
    private String errmsg;
    @ApiModelProperty("错误码，0表示没报错")
    private Integer errno;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    @Override
    public String toString() {
        return "EntrySystemResponse{" +
                "token='" + token + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", errno=" + errno +
                '}';
    }
}
