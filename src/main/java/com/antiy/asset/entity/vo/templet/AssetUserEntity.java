package com.antiy.asset.entity.vo.templet;

import com.antiy.asset.excel.annotation.ExcelField;
import lombok.Data;

@Data
public class AssetUserEntity {


    /**
     * 姓名
     */
    @ExcelField(value = "name", align = 1, title = "姓名", type = 0)
    private String name;


    /**
     * 部门主键
     */
    @ExcelField(value = "departmentId", align = 1, title = "所属部门", type = 0)
    private Integer departmentId;


    /**
     * 电子邮箱
     */
    @ExcelField(value = "email", align = 1, title = "电子邮箱", type = 0)
    private String email;


    /**
     * qq号
     */
    @ExcelField(value = "qq", align = 1, title = "qq号", type = 0)
    private String qq;


    /**
     * 微信
     */
    @ExcelField(value = "weixin", align = 1, title = "微信号", type = 0)
    private String weixin;


    /**
     * 手机号
     */
    @ExcelField(value = "mobile", align = 1, title = "手机号", type = 0)

    private String mobile;


    /**
     * 住址
     */
    @ExcelField(value = "address", align = 1, title = "地址", type = 0)
    private String address;


}
