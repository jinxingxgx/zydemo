package com.xgx.demo.utils;

import java.io.Serializable;

public class LzyResponse<T> implements Serializable {
    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";

    public static final Integer DEFAULT_SUCCESS_CODE = 200;

    public static final Integer DEFAULT_ERROR_CODE = 500;
    private static final long serialVersionUID = 5213230387175987834L;
    /**
     * 请求是否成功
     */
    public Boolean success;

    /**
     * 响应状态码
     */
    public Integer code;

    /**
     * 响应信息
     */
    public String message;
    /**
     * 响应信息
     */
    public String roleName;
    /**
     * 响应信息
     */
    public String deptName;
    /**
     * 响应信息
     */
    public String token;
    /**
     * 响应对象
     */
    public T data;

}