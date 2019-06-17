package com.antiy.asset.vo.response;

import java.util.List;

/**
 * 展示角度类
 */
public class AssetTopologyViewAngle {
    private List<Integer> cameraPos;
    private List<Integer> targetPos;

    public List<Integer> getCameraPos() {
        return cameraPos;
    }

    public void setCameraPos(List<Integer> cameraPos) {
        this.cameraPos = cameraPos;
    }

    public List<Integer> getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(List<Integer> targetPos) {
        this.targetPos = targetPos;
    }
}
