package com.antiy.asset.vo.response;

import java.util.List;

public class AssetTopologyAlarmResponse {
    private List<TopologyAlarm> data;
    private String              status;
    private String              version;

    public List<TopologyAlarm> getData() {
        return data;
    }

    public void setData(List<TopologyAlarm> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public class TopologyAlarm {
        private Integer firewall;
        private String  ip;
        private String  rank;
        private String  asset_id;
        private Integer web;
        private Integer malware;
        private Integer iep;
        private Integer access;
        private Integer mail;
        private Integer loophole;
        private Integer infosystem;
        private String  person_name;
        private Integer communicate;
        private Integer outreach;
        private Integer alert;
        private String  asset_name;
        private Integer c2;
        private Integer database;
        private Integer oa;
        private Integer invade;
        private Integer credit;
        private Integer protect;
        private String  os;

        public Integer getFirewall() {
            return firewall;
        }

        public void setFirewall(Integer firewall) {
            this.firewall = firewall;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(String asset_id) {
            this.asset_id = asset_id;
        }

        public Integer getWeb() {
            return web;
        }

        public void setWeb(Integer web) {
            this.web = web;
        }

        public Integer getMalware() {
            return malware;
        }

        public void setMalware(Integer malware) {
            this.malware = malware;
        }

        public Integer getIep() {
            return iep;
        }

        public void setIep(Integer iep) {
            this.iep = iep;
        }

        public Integer getAccess() {
            return access;
        }

        public void setAccess(Integer access) {
            this.access = access;
        }

        public Integer getMail() {
            return mail;
        }

        public void setMail(Integer mail) {
            this.mail = mail;
        }

        public Integer getLoophole() {
            return loophole;
        }

        public void setLoophole(Integer loophole) {
            this.loophole = loophole;
        }

        public Integer getInfosystem() {
            return infosystem;
        }

        public void setInfosystem(Integer infosystem) {
            this.infosystem = infosystem;
        }

        public String getPerson_name() {
            return person_name;
        }

        public void setPerson_name(String person_name) {
            this.person_name = person_name;
        }

        public String getAsset_name() {
            return asset_name;
        }

        public void setAsset_name(String asset_name) {
            this.asset_name = asset_name;
        }

        public Integer getC2() {
            return c2;
        }

        public void setC2(Integer c2) {
            this.c2 = c2;
        }

        public Integer getDatabase() {
            return database;
        }

        public void setDatabase(Integer database) {
            this.database = database;
        }

        public Integer getOa() {
            return oa;
        }

        public void setOa(Integer oa) {
            this.oa = oa;
        }

        public Integer getInvade() {
            return invade;
        }

        public void setInvade(Integer invade) {
            this.invade = invade;
        }

        public Integer getCredit() {
            return credit;
        }

        public void setCredit(Integer credit) {
            this.credit = credit;
        }

        public Integer getProtect() {
            return protect;
        }

        public void setProtect(Integer protect) {
            this.protect = protect;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public Integer getCommunicate() {
            return communicate;
        }

        public void setCommunicate(Integer communicate) {
            this.communicate = communicate;
        }

        public Integer getOutreach() {
            return outreach;
        }

        public void setOutreach(Integer outreach) {
            this.outreach = outreach;
        }

        public Integer getAlert() {
            return alert;
        }

        public void setAlert(Integer alert) {
            this.alert = alert;
        }
    }
}
