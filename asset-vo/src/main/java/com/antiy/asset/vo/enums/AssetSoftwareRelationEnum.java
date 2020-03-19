package com.antiy.asset.vo.enums;

import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AssetSoftwareRelationEnum {
    RELATED(1,"已关联"),UNRELATED(2,"未关联");
    int code;
    String msg;
    AssetSoftwareRelationEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    public static String getMsgByCode(int code){
        List<String> collect = Arrays.stream(AssetSoftwareRelationEnum.values()).filter(v -> v.getCode()==code).map(t -> t.getMsg()).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(collect))
            return  collect.get(0);
        return null;
    }
}
