package com.antony.config;

public class AliyunSmsSenderImpl implements SmsSender {

    private SmsMessage smsMessage;

    public AliyunSmsSenderImpl(SmsMessage smsProperties) {
        this.smsMessage = smsProperties;
    }

    @Override
    public boolean send(String message) {
        System.out.println(smsMessage.toString()+"开始发送短信==》短信内容："+message);
        return true;
    }
}