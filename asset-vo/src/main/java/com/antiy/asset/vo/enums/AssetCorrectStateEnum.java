package com.antiy.asset.vo.enums;

public enum AssetCorrectStateEnum {

        HAD_DONE_CORRECT(1,"已整改"),
    NEED_CORRECT(2,"未整改"),
    CORRECTING(3,"整改中"),
    HAS_DONE_CORRECT(4,"完成整改")
    ;
     AssetCorrectStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
