package com.johnchan.rpc.common.entity;

import com.johnchan.rpc.common.enumration.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.common.entity
 * @className: RpcResponse
 * @author: johnchan
 * @description: 服务者执行完成或者出错需要向消费者返回结果信息
 * @date: 2023/6/6 21:43
 * @version: 1.0
 */
@Data
public class RpcResponse<T> implements Serializable {

    // 响应状态码
    private Integer statusCode;

    // 响应的信息
    private String message;

    // 响应数据
    private T data;

    public static<T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<T>();
        response.setData(data);
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        return response;
    }

    public static<T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<T>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
