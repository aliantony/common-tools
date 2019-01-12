package com.antiy.asset.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
     * 反射类型
     *
     * @return
     */
    Class<?> fieldType() default Class.class;
}
