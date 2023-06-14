package com.johnchan.rpc.core.server;

import com.johnchan.rpc.common.entity.RpcRequest;
import com.johnchan.rpc.common.entity.RpcResponse;
import com.johnchan.rpc.common.enumration.ResponseCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.server
 * @className: RequestHandler
 * @author: johnchan
 * @description: TODO
 * @date: 2023/6/12 23:42
 * @version: 1.0
 */
@Slf4j
public class RequestHandler {
    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest, service);
            log.info("service :{} succeed to use method:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("error happens", e);
            e.printStackTrace();
        }
        return result;
    }


    public Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
        }
        return method.invoke(service, rpcRequest.getParameters());

    }
}
