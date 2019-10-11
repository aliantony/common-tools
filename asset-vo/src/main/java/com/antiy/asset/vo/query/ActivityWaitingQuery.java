package com.antiy.asset.vo.query;

/**
 * @auther: zhangbing
 * @date: 2019/1/29 09:41
 * @description: 流程引擎查询代办任务条件
 */
public class ActivityWaitingQuery {

    /**
     * 当前登录用户Id
     */
    private String user;

    /**
     * 流程定义key，安全检查和模板实施都是assetAdmittance
     */
    private String processDefinitionKey;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }
}
