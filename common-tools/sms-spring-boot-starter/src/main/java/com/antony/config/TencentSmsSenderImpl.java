package com.antony.config;

public class TencentSmsSenderImpl implements SmsSender {

    private SmsMessage smsMessage;

    public TencentSmsSenderImpl(SmsMessage smsProperties) {
        this.smsMessage = smsProperties;
    }

    @Override
    public boolean send(String message) {
        System.out.println(smsMessage.toString()+"开始发送短信==》短信内容："+message);
        return true;
    }
}