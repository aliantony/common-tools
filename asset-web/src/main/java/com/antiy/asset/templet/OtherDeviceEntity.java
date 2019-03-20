package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

public class OtherDeviceEntity {
    /**
     * area
     */
    @ExcelField(value = "area", align = 1, title = "所属区域",required = true)
    private String                  area;
    /**
     * 资产编号
     */
    @ExcelField(value = "number", align = 1, title = "资产编号")
    private String                  number;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称")
    private String name;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商")
    private String manufacturer;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String serial;

    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者")
    private String user;
    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话",type = 0)
    private String telephone;
    /**
     * 使用者邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱",type = 0)
    private String email;

    /**
     * 购买日期
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买日期",isDate = true)
    private Long            buyDate;
    /**
     * 到期时间
     */
    @ExcelField(value = "due_date", align = 1, title = "到期时间",isDate = true,required = false)
    private Long            dueDate;
    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期",isDate = true)
    private Long            warranty;
    /**
     * 描述
     */
    @ExcelField(value = "memo", align = 1, title = "描述")
    private String            memo;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public Long getWarranty() {
        return warranty;
    }

    public void setWarranty(Long warranty) {
        this.warranty = warranty;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
