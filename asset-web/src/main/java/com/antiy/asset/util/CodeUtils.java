package com.antiy.asset.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.antiy.biz.enums.ModuleEnum;
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

    public static Map<String, Map<Integer, String>> codeMap  = new HashMap();
    public static Map<Integer, String>              code     = new HashMap(16);
    private static Boolean                          initflag = false;

    public static void init() {
        if (!initflag) {
            String key = RedisKeyUtil.getKeyWhenGetObjectsByKeyword(ModuleEnum.COMMON.getType(), CodeType.class);
            try {
                RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
                List<CodeType> codeTypeList = redisUtil.getObjectsByKeyword(key, CodeType.class);
                for (CodeType codeType : codeTypeList) {
                    if (codeType.getId() <= 996 && codeType.getId() >= 982) {
                        String ke = RedisKeyUtil.getKeyWhenHandleObjectList(ModuleEnum.COMMON.getType(), "codeTypeId",
                            codeType.getId().toString(), Code.class);
                        List<Code> list = redisUtil.getObjectList(ke, Code.class);
                        code = new HashMap<>(16);
                        for (Code cod : list) {
                            code.put(cod.getCode(), cod.getValue());
                        }
                        codeMap.put(codeType.getCode(), code);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            code = new HashMap(16);
            code.put(1, "是");
            code.put(0, "否");
            codeMap.put("yesorno", code);
            initflag = true;
        }

    }

    /**
     * 获取code码
     *
     * @param codeType
     * @param codeName
     * @return
     */
    public static Integer getCodeValue(String codeType, String codeName) {
        init();
        if (StringUtils.isEmpty(codeType) || StringUtils.isEmpty(codeName)) {
            return null;
        }
        Map<Integer, String> map = codeMap.get(codeType);
        for (Map.Entry entry : map.entrySet()) {
            if (codeName.equals(entry.getValue())) {
                return (Integer) entry.getKey();
            }
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
        init();
        if (StringUtils.isEmpty(codeType) || codeValue == null) {
            return "";
        }
        Map<Integer, String> map = codeMap.get(codeType);

        return map.get(codeValue);
    }

    /**
     * 获取code码
     *
     * @param codeType
     * @return
     */
    public static List<String> getCodeList(String codeType) {
        init();
        if (StringUtils.isEmpty(codeType)) {
            return null;
        }
        List<String> list = Lists.newArrayList();
        Map<Integer, String> map = codeMap.get(codeType);
        for (Map.Entry entry : map.entrySet()) {
            list.add(entry.getValue().toString());
        }
        return list;
    }

    /**
     * 获取code码
     *
     * @param codeType
     * @return
     */
    public static String[] getCodeArray(String codeType) {
        init();
        List list = getCodeList(codeType);
        if (list == null || list.isEmpty()) {
            return null;
        }
        String[] strings = (String[]) list.toArray(new String[list.size()]);
        return strings;
    }
}
