package com.llspace.seckill.utils;

import lombok.Data;

/**
 * <p>@filename Result</p>
 * <p>
 * <p>@description 通用返回结果封装</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/17 11:55
 **/
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

}
