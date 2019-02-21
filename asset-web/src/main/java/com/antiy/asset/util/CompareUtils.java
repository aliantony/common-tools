package com.antiy.asset.util;

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @link 原文：https://blog.csdn.net/liu_ling1216/article/details/80207985
 *
 * @author zhangyajun
 * @create 2019-02-19 17:31
 **/
public class CompareUtils {
    /**
     * 获取两个对象同名属性内容不相同的列表
     * @param class1 对象1
     * @param class2 对象2
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public static List<Map<String, Object>> compareClass(Object class1, Object class2, String label) throws ClassNotFoundException,
                                                                                          IllegalAccessException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 获取对象的class
        Class<?> clazz1 = class1.getClass();
        Class<?> clazz2 = class2.getClass();
        // 获取对象的属性列表
        Field[] field1 = clazz1.getDeclaredFields();
        Field[] field2 = clazz2.getDeclaredFields();
        // 遍历属性列表field1
        for (int i = 0; i < field1.length; i++) {
            // 遍历属性列表field2
            for (int j = 0; j < field2.length; j++) {
                // 如果field1[i]属性名与field2[j]属性名内容相同
                if (field1[i].getName().equals(field2[j].getName())) {
                    field1[i].setAccessible(true);
                    field2[j].setAccessible(true);
                    // 如果field1[i]属性值与field2[j]属性值内容不相同
                    if (!compare(field1[i].get(class1), field2[j].get(class2))) {
                        Map<String, Object> map2 = new HashMap<String, Object>();
                        ApiModelProperty apiModelProperty = field1[i].getAnnotation(ApiModelProperty.class);
                        map2.put("name", apiModelProperty != null ? apiModelProperty.value() : field1[i].getName());
                        map2.put("old", field1[i].get(class1));
                        map2.put("new", field2[j].get(class2));
                        map2.put("label", label);
                        Object oldValue = field1[i].get(class1);
                        Object newValue = field2[j].get(class2);
                        if (oldValue!= null && !oldValue.equals(newValue)){
                            map2.put("operation",  "更改" );
                        }else if (oldValue != null && newValue == null){
                            map2.put("operation",  "删除" );
                        }else if (oldValue == null && newValue != null){
                            map2.put("operation",  "新增" );
                        }

                        list.add(map2);
                    }
                    break;
                }
            }
        }

        return list;
    }

    /**
     * 对比两个数据是否内容相同
     *
     * @param object1
     * @param object2
     * @return
     */
    public static boolean compare(Object object1, Object object2) {

        if (object1 == null && object2 == null) {
            return true;
        }
        // 以下注掉代码，看具体需求。因有时会出现源数据是没有进行赋值，因此是null；而通过EditText获取值的时候，虽然没有值，但是会变成""，但是本质是没有赋值的。
        // if (object1 == "" && object2 == null) {
        // return true;
        // }
        // if (object1 == null && object2 == "") {
        // return true;
        // }
        if (object1 == null && object2 != null) {
            return false;
        }
        if (object1.equals(object2)) {
            return true;
        }
        return false;
    }
}
