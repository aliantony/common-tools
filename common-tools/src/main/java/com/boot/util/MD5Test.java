package com.boot.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "123";
        byte[] result = getMD5Bytes(s.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte temp : result) {
            if (temp >= 0 && temp < 16) {
                stringBuilder.append("0");
            }
            stringBuilder.append(Integer.toHexString(temp & 0xff));
        }
        System.out.println(s + ",MD5加密后:" + stringBuilder.toString());
    }

    private static byte[] getMD5Bytes(byte[] content) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(content);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}