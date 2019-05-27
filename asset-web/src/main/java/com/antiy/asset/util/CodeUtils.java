package com.antiy.asset.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.antiy.biz.entity.Code;
import com.antiy.biz.entity.CodeType;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.utils.SpringUtil;

@Component
public class CodeUtils {

    /**
     * 获取code码
     *
     * @param codeType
     * @param codeName
     * @return
     */
    public static Integer getCodeValue(String codeType, String codeName) {
        String key = RedisKeyUtil.getKeyWhenOperateObjectList(ModuleEnum.COMMON.getType(), codeType, Code.class);
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        try {
            List<Code> codeList = redisUtil.getObjectList(key, Code.class);
            for (Code code : codeList) {
                if (codeName.equals(code.getValue())) {
                    return Integer.parseInt(code.getCode());
                }
            }
        } catch (Exception e) {
            throw new BusinessException("获取码表信息失败");
        }
        return null;
    }

    /**
     * 获取code名称
     *
     * @param codeType
     * @param codeValue
     * @return
     */
    public static String getCodeName(String codeType, Integer codeValue) {
        String key = RedisKeyUtil.getKeyWhenOperateObjectList(ModuleEnum.COMMON.getType(), codeType, Code.class);
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        try {
            List<Code> codeList = redisUtil.getObjectList(key, Code.class);
            for (Code code : codeList) {
                if (codeValue.equals(Integer.valueOf(code.getCode()))) {
                    return code.getValue();
                }
            }
        } catch (Exception e) {
            throw new BusinessException("获取码表信息失败");
        }
        return null;
    }

    /**
     * 获取code码
     *
     * @param codeType
     * @return
     */
    public static List<String> getCodeList(String codeType) {
        String key = RedisKeyUtil.getKeyWhenOperateObjectList(ModuleEnum.COMMON.getType(), codeType, Code.class);
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        try {
            List<Code> codeList = redisUtil.getObjectList(key, Code.class);
            List<String> list = new ArrayList<>();
            for (Code code : codeList) {
                list.add(code.getValue());
            }
            return list;
        } catch (Exception e) {
            throw new BusinessException("获取码表信息失败");
        }
    }

    /**
     * 获取code码
     *
     * @param codeType
     * @return
     */
    public static String[] getCodeArray(String codeType) {
        String key = RedisKeyUtil.getKeyWhenOperateObjectList(ModuleEnum.COMMON.getType(), codeType, Code.class);
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        try {
            List<Code> codeList = redisUtil.getObjectList(key, Code.class);
            String[] s = new String[codeList.size()];
            int i = 0;
            for (Code code : codeList) {
                s[i] = code.getValue();
                i++;
            }
            return s;
        } catch (Exception e) {
            throw new BusinessException("获取码表信息失败");
        }
    }
}
