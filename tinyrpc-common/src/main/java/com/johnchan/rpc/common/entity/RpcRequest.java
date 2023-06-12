package com.johnchan.rpc.common.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.common.entity
 * @className: RpcRequest
 * @author: johnchan
 * @description: 消费者向服务者提供的请求对象，需要进行序列化
 * @date: 2023/6/6 21:31
 * @version: 1.0
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    // 待调用的接口名称
    private String interfaceName;

    // 待调用方法的名称
    private String methodName;

    // 方法的参数
    private Object[] parameters;

    // 调用方法的参数类型
    private Class<?>[] paramTypes;
}
