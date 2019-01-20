package com.antiy.asset.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.antiy.common.base.BaseConverter;

/**
 * 主键转换器
 *
 * @author zhangyajun
 * @create 2019-01-12 13:21
 **/
@Component
public class IDRequestConverter extends BaseConverter<String, Integer> {
    @Override
    public List<Integer> convert(List<String> list, Class<Integer> clazz) {
        List<Integer> idIntList = new ArrayList<>();
        if (list != null && list.size() >0 ){
            for (String id : list){
                idIntList.add(Integer.valueOf(id));
            }
        }
        return idIntList;
    }
}
