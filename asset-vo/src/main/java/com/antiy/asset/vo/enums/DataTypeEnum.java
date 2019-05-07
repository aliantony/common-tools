package com.antiy.asset.vo.enums;

public enum DataTypeEnum {
                          NONE("NONE", "无类型"), EMAIL("EMAIL",
                                                     "邮箱"), TEL("TEL",
                                                                "电话"), IDCARD("IDCARD",
                                                                              "身份证号"), IP("IP",
                                                                                          "IP地址"), MAC("MAC", "MAC地址"),;

    private String type;
    private String name;

    DataTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 校验数据格式
     * @param val
     * @param type
     * @return
     */
    public static boolean validate(String val, DataTypeEnum type) {
        String reg;
        switch (type) {
            case NONE:
                return true;
            case EMAIL:
                reg = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
                return val.matches(reg);
            case TEL:
                reg = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
                return val.matches(reg);
            case IDCARD:
                reg = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
                return val.matches(reg);
            case IP:
                reg = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$";
                return val.matches(reg);
            case MAC:
                reg = "/(([a-f0-9]{2}:)|([a-f0-9]{2}-)){5}[a-f0-9]{2}/gi";
                return val.matches(reg);
            default:
                return true;
        }
    }
}
