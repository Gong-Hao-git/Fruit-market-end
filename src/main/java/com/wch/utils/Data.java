package com.wch.utils;
/**
 * 定义返回的数据类型
 * @param <T>
 */
public class Data<T> {
    private int code;
    private T data;

    public Data() {
    }

    public Data(int code) {
        this.code = code;
        this.data = data;
    }

    public Data(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "code=" + code +
                ", data='" + data + '\'' +
                '}';
    }
}
