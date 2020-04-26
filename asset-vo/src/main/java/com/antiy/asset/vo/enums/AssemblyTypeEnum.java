package com.antiy.asset.vo.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 组件类型枚举
 *
 */
public enum AssemblyTypeEnum {
    //CPU、硬盘、内存、主板、网卡、声卡、显卡、光驱、显示器、RAID卡、其他
    CPU("CPU", "CPU"),
    DISK("DISK", "硬盘"),
    MEMORY("MEMORY", "内存"),
    MAINBOARD("MAINBOARD", "主板"),
    NETWORK_CARD("NETWORK_CARD", "网卡"),
    SOUND_CARD("SOUND_CARD", "声卡"),
    GRAPHICS_CARD("GRAPHICS_CARD", "显卡"),
    CD_DRIVER("CD_DRIVER", "光驱"),
    DISPLAY_DEVICE("DISPLAY_DEVICE", "显示器"),
    RAID("RAID", "RAID卡"),
    OTHER("OTHER", "其他");
    private String code;
    private String name;

    AssemblyTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getCodeByName(String name){
        for (AssemblyTypeEnum typeEnum : AssemblyTypeEnum.values()) {
            if (Objects.equals(typeEnum.name,name)){
                return typeEnum.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code){
        for (AssemblyTypeEnum typeEnum : AssemblyTypeEnum.values()) {
            if (Objects.equals(typeEnum.code,code)){
                return typeEnum.name;
            }
        }
        return null;
    }

    public static  List<String> getNameList(){
        List<String> collect = Arrays.stream(AssemblyTypeEnum.values()).map(assemblyTypeEnum -> assemblyTypeEnum.name).collect(Collectors.toList());
        return collect;
    }
}
