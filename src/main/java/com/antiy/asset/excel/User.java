package com.antiy.asset.excel;

import com.antiy.asset.excel.annotation.ExcelField;

import java.util.Date;

public class User {
    @ExcelField(value = "name", align = 1, title = "姓名", type = 0)
    private String name;
    @ExcelField(value = "age", align = 1, title = "年龄", type = 0)
    private int age;
    @ExcelField(value = "addr", align = 1, title = "住址", type = 0, dictType = "aaa")
    private String addr;
    @ExcelField(value = "birth", align = 1, title = "生日", type = 0)
    private Date birth;

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public User() {}

    public User(String name, int age, String addr, Date birth) {
        this.name = name;
        this.age = age;
        this.addr = addr;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
