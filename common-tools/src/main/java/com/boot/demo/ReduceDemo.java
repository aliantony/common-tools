package com.boot.demo;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program common-tools
 * @description 
 * @author wq
 * created on 2020-08-06
 * @version  1.0.0
 */
public class ReduceDemo {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("name=zhagnsan", "age=28", "national=china");
        Map<String, String> maps = list.stream().map(e -> {
            String[] ss = e.split("=", 2);
            Map map = new HashMap();
            map.put(ss[0], ss[1]);
            return map;
            /**
             * 第一个参数是累加结果，m代表上一次累加的值
             */
        }).reduce(new HashMap<String, String>(), (m, kv) -> {
            m.putAll(kv);
            return m;
        });
        maps.forEach((k, v) -> System.out.println(k + "=" + v));
    }
}
