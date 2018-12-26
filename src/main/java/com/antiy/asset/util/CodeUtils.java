package com.antiy.asset.util;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeUtils {

    public static Map<String, Map<Integer, String>> codeMap = new HashMap();
    public static Map<Integer, String> code = new HashMap(16);

    static {
        init();
    }
    public static void init() {

        code.put(1, "待登记");
        code.put(2, "不予登记");
        code.put(3, "待配置");
        code.put(4, "待验证");
        code.put(6, "待入网");
        code.put(5, "已入网");
        code.put(7, "待退役");
        code.put(8, "已退役");
        codeMap.put("hardware_status", code);

        code = new HashMap(16);
        code.put(1, "台式办公机");
        code.put(2, "便携式办公机");
        code.put(3, "服务器");
        code.put(4, "移动设备");
        code.put(5, "ATM机");
        code.put(6, "工控上位机");
        code.put(7, "路由器");
        code.put(8, "交换机");
        code.put(9, "防火墙");
        code.put(10, "IDS");
        code.put(11, "IPS");
        code.put(12, "虚拟终端");
        code.put(13, "未设置");
        codeMap.put("hardware_type", code);

        code = new HashMap(16);
        code.put(1, "待登记");
        code.put(2, "不予登记");
        code.put(3, "待配置");
        code.put(4, "待验证");
        code.put(6, "待入网");
        code.put(5, "已入网");
        code.put(7, "待退役");
        code.put(8, "已退役");
        codeMap.put("software_status", code);

        code = new HashMap(16);
        code.put(1, "免费软件");
        code.put(2, "商业软件");
        codeMap.put("authorization", code);

        code = new HashMap(16);
        code.put(1, "SATA");
        code.put(2, "IDE");
        code.put(3, "ATA");
        codeMap.put("interface_type", code);

        code = new HashMap(16);
        code.put(1, "不重要");
        code.put(2, "一般");
        code.put(3, "重要");
        codeMap.put("major_type", code);

        code = new HashMap(16);
        code.put(1, "自动上报");
        code.put(2, "人工上报");
        codeMap.put("report_source", code);

        code = new HashMap(16);
        code.put(1, "是");
        code.put(0, "否");
        codeMap.put("yesorno", code);
    }

    /**
     * 获取code码
     * @param codeType
     * @param codeName
     * @return
     */
    public static Integer getCodeValue(String codeType, String codeName) {
        if (StringUtils.isEmpty(codeType) ||  StringUtils.isEmpty(codeName)) {
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
     * @param codeType
     * @param codeValue
     * @return
     */
    public static String getCodeName(String codeType, Integer codeValue) {
        if (StringUtils.isEmpty(codeType) ||  codeValue == null) {
            return "";
        }
        Map<Integer, String> map = codeMap.get(codeType);

        return map.get(codeValue);
    }

    /**
     * 获取code码
     * @param codeType
     * @return
     */
    public static List<String> getCodeList(String codeType) {
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
     * @param codeType
     * @return
     */
    public static String[] getCodeArray(String codeType) {

        List list = getCodeList(codeType);
        String[] strings = (String[]) list.toArray(new String[list.size()]);
        return strings;
    }
}
