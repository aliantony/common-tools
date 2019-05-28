package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import java.util.List;

/**
 * @Author: lvliang
 * @Date: 2019/5/28 9:40
 */
public class IDResponse extends BaseResponse {
    @Encode
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "IDResponse{" + "ids=" + ids + '}';
    }
}
