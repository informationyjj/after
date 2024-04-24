package com.example.demo.common;

import lombok.Data;

@Data
public class R<T> {

    private Integer code; //编码：200成功，100和其它数字为失败

    private String msg; //错误信息

    private T data; //数据


    public static <T> R<T> success(String msg) {
        R<T> r = new R<T>();
        r.data = null;
        r.code = 200;
        r.msg = msg;
        return r;
    }

    public static <T> R<T> success(T object, String msg) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 200;
        r.msg = msg;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 100;
        return r;
    }


}

