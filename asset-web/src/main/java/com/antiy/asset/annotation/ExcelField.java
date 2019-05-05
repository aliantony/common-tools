package com.antiy.asset.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.antiy.asset.vo.enums.DataTypeEnum;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    /**
     * 导出字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
     *
     * @return
     */
    String value() default "";

    /**
     * 是否必填：true必填，false不必填
     *
     * @return
     */
    boolean required() default false;

    /**
     * 标题
     *
     * @return
     */
    String title() default "";

    /**
     * 导入导出类型：（0：导出导入；1：仅导出；2：仅导入（针对导出模板））
     *
     * @return
     */
    int type() default 0;

    /**
     * 对齐方式：（0：自动；1：靠左；2：居中；3：靠右）
     *
     * @return
     */
    int align() default 0;

    /**
     * 字段排序 （升序）
     *
     * @return
     */
    int sort() default 0;

    /**
     * 如果是码表类型，请设置码表的type值
     *
     * @return
     */
    String dictType() default "";

    /**
     * 是否是日期 false：否；true：是；
     *
     * @return
     */
    boolean isDate() default false;

    /**
     * 是否是父ID
     *
     * @return
     */
    boolean isParent() default false;

    /**
     * 字段长度
     * @return
     */
    int length() default 2147483647;

    /**
     * 字段校验类型
     * @return
     */
    DataTypeEnum dataType() default DataTypeEnum.NONE;

    /**
     * 反射类型
     *
     * @return
     */
    Class<?> fieldType() default Class.class;

    /**
     * 导出表格为下拉框，下拉框值来自于数据库，此处填写调用获取数据库的方法名，并且返回的值为list<string>,和dictType码表类型不能同时存在，如果存在优先以defaultDataMethod为准
     * @return
     */
    String defaultDataMethod() default "";

    /**
     * 导出表格默认的javaBean的名字，需要是实现类的名字才可以。
     * @return
     */
    String defaultDataBeanName() default "";
}
