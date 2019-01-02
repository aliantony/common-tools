package com.antiy.asset.vo.templet;

import com.antiy.asset.excel.annotation.ExcelField;

public class AssetDepartmentEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 部门名
     */
    @ExcelField(value = "name", align = 1, title = "部门名", type = 0)
    private String name;


    /**
     * 负责人
     */
    @ExcelField(value = "responsibleUser", align = 1, title = "负责人", type = 0)
    private String responsibleUser;


    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "电话号码", type = 0)
    private String telephone;


    /**
     * 上级部门
     */
    @ExcelField(value = "parentId", align = 1, title = "上级部门", type = 0)
    private Integer parentId;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }



}
