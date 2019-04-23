package com.antiy.asset.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.antiy.common.base.BusinessData;
import com.antiy.common.utils.LogUtils;

/**
 * @ClassName: LogHandle
 * @Auther: chenguoming
 * @Date: 2019/1/19 10:37
 * @Description: 业务日志处理
 */
public class LogHandle {

    private final static Integer MODULE_ID = 1;

    /**
     * @Description 业务日志记录
     * @Date 13:28 2019/1/19
     * @param targetObj 操作对象
     * @param eventDesc 操作描述
     * @param businessPhase 操作对象的业务阶段状态
     * @param module 模块标识
     * @return void
     */
    public static void log(Object targetObj, String eventDesc, Integer businessPhase, Integer module) {
        if (Objects.isNull(targetObj)) {
            return;
        }
        // 判断操作对象是否为集合，是集合则表明是批量操作
       /* if (targetObj instanceof List) {
            List list = (List) targetObj;
            list.forEach(obj -> {
               *//* BusinessData logData = new BusinessData(eventDesc, getObjId(targetObj), targetObj.toString(), module,
                    businessPhase);*//*
                LogUtils.recordOperLog(logData);
            });
        } else {
            BusinessData logData = new BusinessData(eventDesc, getObjId(targetObj), targetObj.toString(), module,
                businessPhase);
            LogUtils.recordOperLog(logData);
        }*/
    }

    /**
     * @Description 根据反射获取操作对象的ID
     * @Date 13:31 2019/1/19
     * @param obj
     * @return java.lang.Integer
     */
    private static Integer getObjId(Object obj) {
        // 因为传递的类是继承自BaseEntiy
        Class<?> clz = obj.getClass().getSuperclass();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clz.getDeclaredFields();
        Field field = Arrays.asList(fields).stream().filter(tmp -> "id".equals(tmp.getName())).findFirst().orElse(null);
        if (Objects.nonNull(field)){
            try {
                field.setAccessible(true);
                return (Integer) field.get(obj);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return 0;
    }

}
