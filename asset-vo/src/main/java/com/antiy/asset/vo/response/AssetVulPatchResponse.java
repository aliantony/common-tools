package com.antiy.asset.vo.response;

import java.util.List;

public class AssetVulPatchResponse {
    private List<Integer> vulList;
    private List<Integer> patchList;

    public List<Integer> getVulList() {
        return vulList;
    }

    public void setVulList(List<Integer> vulList) {
        this.vulList = vulList;
    }

    public List<Integer> getPatchList() {
        return patchList;
    }

    public void setPatchList(List<Integer> patchList) {
        this.patchList = patchList;
    }
}
