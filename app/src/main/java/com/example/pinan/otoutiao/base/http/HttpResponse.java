package com.example.pinan.otoutiao.base.http;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 * @author pinan
 */
public class HttpResponse {
    
    /**
     * 描述信息
     */
    @SerializedName("msg")
    public String msg;
    
    /**
     * 状态码
     */
    @SerializedName("retCode")
    public int code;
    
    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("result")
    public Object result;
    
    /**
     * 是否成功(这里约定200)
     *
     * @return
     */
    public boolean isSuccess() {
        return code == 200 ? true : false;
    }
    
    @Override
    public String toString() {
        String response = "[http response]\n" + "{\"code\": " + code + ",\n\"msg\": " + msg + ",\n\"result\": " + new Gson().toJson(result) + "}";
        return response;
    }
}