package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

public class KeyEntity {

    /**
     * 资产编号
     */
    @ExcelField(value = "keyNum", align = 1, title = "key编号(必填)", required = true, length = 64)
    private String keyNum;


    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }
}
