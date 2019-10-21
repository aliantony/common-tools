package com.antiy.asset.vo.enums;

public enum AssetOperationSystemEnum {
    OS_X_SERVER(1,"os_x_server","macOS"),
    MAC_OS_X(2,"mac_os_x","macOS"),
    HP_UX(3,"hp-ux","hp-ux"),
    AIX(4,"aix","aix"),
    ENTERPRISE_LINUX(4,"enterprise_linux","linux"),
    LINUX_2(4,"linux_2","linux"),
    DEBIAN_LINUX(4,"debian_linux","linux"),
    WINDOWS_NT(4," windows-nt","windows"),
    WINDOWS_7(4,"windows_7","windows"),
    WINDOWS_8(4,"windows_8","windows"),
    WINDOWS_8_1(4,"windows_8.1","windows"),
    WINDOWS_10(4,"windows_10","windows"),
    WINDOWS_SERVER_2008(4,"windows_server_2008","windows"),
    WINDOWS_SERVER_2012(4,"windows_server_2012","windows"),
    WINDOWS_SERVER_2016(4,"windows_server_2016","windows"),
    WINDOWS_SERVER_2003(4,"windows_server_2003","windows"),
    LINUX_ENTERPRISE_SERVER(4,"linux_enterprise_servert","linux"),
    SUSE_LINUX_ENTERPRISE_SERVER(4,"suse_linux_enterprise_server","linux"),
    UBUNTU_LINUX(4,"ubuntu_linux","linux");

    private Integer code;
    private String osName;
    private String serialName;

    AssetOperationSystemEnum(Integer code, String osName, String serialName) {
        this.code = code;
        this.osName = osName;
        this.serialName = serialName;
    }

    public Integer getCode() {
        return code;
    }

    public String getOsName() {
        return osName;
    }

    public String getSerialName() {
        return serialName;
    }
}
