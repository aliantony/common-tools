package com.antiy.asset.vo.response;

import java.util.List;

/**
 * 展示角度类
 */
public class AssetTopologyViewAngle {
    private List<Double> cameraPos;
    private List<Double> targetPos;

    public List<Double> getCameraPos() {
        return cameraPos;
    }

    public void setCameraPos(List<Double> cameraPos) {
        this.cameraPos = cameraPos;
    }

    public List<Double> getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(List<Double> targetPos) {
        this.targetPos = targetPos;
    }
}
