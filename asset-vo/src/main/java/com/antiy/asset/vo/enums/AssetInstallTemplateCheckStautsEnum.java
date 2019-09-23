package com.antiy.asset.vo.enums;

public enum  AssetInstallTemplateCheckStautsEnum {
    SUBMIT_AUDIT(1,"提交审核"),
    FORBIDDEN(2,"拒绝"),
    AUDIT_PASS(3,"审核通过");
    AssetInstallTemplateCheckStautsEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public static AssetInstallTemplateCheckStautsEnum getStatusEnumByCode(Integer code){
        if(code==null)
            return null;
        for(AssetInstallTemplateCheckStautsEnum statusEnum:AssetInstallTemplateCheckStautsEnum.values()){
            if(statusEnum.getCode().equals(code))
                return statusEnum;
        }
        return null;
    }
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
