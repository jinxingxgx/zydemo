package com.xgx.demo.utils;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

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

    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.code = code;
        lzyResponse.message = message;
        return lzyResponse;
    }
}