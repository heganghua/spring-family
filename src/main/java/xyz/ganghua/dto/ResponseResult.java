package xyz.ganghua.dto;

import java.io.Serializable;

import xyz.ganghua.enums.HttpEnum;

public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public static <T> ResponseResult<T> ok() {
        return restResult(null, HttpEnum.OK_200.code(), HttpEnum.OK_200.desc());
    }

    public static <T> ResponseResult<T> ok(T data) {
        return restResult(data, HttpEnum.OK_200.code(), HttpEnum.OK_200.desc());
    }

    public static <T> ResponseResult<T> ok(T data, String msg) {
        return restResult(data, HttpEnum.OK_200.code(), msg);
    }

    public static <T> ResponseResult<T> failed() {
        return restResult(null, HttpEnum.ERROR_500.code(), HttpEnum.ERROR_500.desc());
    }

    public static <T> ResponseResult<T> failed(String msg) {
        return restResult(null, HttpEnum.ERROR_500.code(), msg);
    }

    public static <T> ResponseResult<T> failed(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> ResponseResult<T> failed(T data) {
        return restResult(data, HttpEnum.ERROR_500.code(), HttpEnum.ERROR_500.desc());
    }

    public static <T> ResponseResult<T> failed(T data, String msg) {
        return restResult(data, HttpEnum.ERROR_500.code(), msg);
    }

    private static <T> ResponseResult<T> restResult(T data, int code, String msg) {
        ResponseResult<T> apiResult = new ResponseResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
