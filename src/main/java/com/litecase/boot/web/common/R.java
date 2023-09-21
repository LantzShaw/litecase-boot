package com.litecase.boot.web.common;

public class R<T> {
    // 状态码
    private Integer code;
    // 错误信息
    private String msg;
    // 响应数据
    private T data;

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 200;

        return  r;
    }

    public static <T> R<T> error(String message) {
        R<T> r = new R<>();
        r.code = 999;
        r.msg = message;

        return r;
    }
}
