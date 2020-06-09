package com.boot.model;

import lombok.Data;

/**
 * @program demo1
 * @description 
 * @author wangqian
 * created on 2020-05-06
 * @version  1.0.0
 */
@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result() {

    }

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public void SetResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public static Result success() {
        Result result = new Result();
        result.SetResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.SetResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.SetResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.SetResultCode(resultCode);
        result.setData(data);
        return result;
    }
}
