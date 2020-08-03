package com.antony.config;

import lombok.Data;

@Data
public class SmsMessage {


    /**
     * 用户名
     */
    private String userName = "defalut";

    /**
     * 密码
     */
    private String passWord;

    /**
     * 秘钥
     */
    private String sign;

    /**
     *
     */
    private String url;

    @Override
    public String toString() {
        return "SmsMessage{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", sign='" + sign + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}