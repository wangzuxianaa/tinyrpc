package com.johnchan.rpc.core.client;

import com.johnchan.rpc.common.entity.RpcRequest;
import com.johnchan.rpc.common.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.client
 * @className: RpcClientProxy
 * @author: johnchan
 * @description:  RpcClient客户端的代理类
 * @date: 2023/6/7 0:20
 * @version: 1.0
 */
public class RpcClientProxy implements InvocationHandler {

    private String host;

    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();

        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse)rpcClient.sendRequest(rpcRequest, host, port)).getData();
    }
}
