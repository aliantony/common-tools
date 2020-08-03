package com.antony.config;

/**
 * @author wq
 * created on 2020-08-03
 * @version 1.0.0
 * @program common-tools
 * @description
 */
public interface SmsSender {
    boolean send(String message);
}
