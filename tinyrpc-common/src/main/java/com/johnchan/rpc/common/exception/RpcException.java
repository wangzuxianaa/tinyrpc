package com.johnchan.rpc.common.exception;

import com.johnchan.rpc.common.enumration.RpcError;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.common.exception
 * @className: RpcException
 * @author: Eric
 * @description: RPC调用异常
 * @date: 2023/6/12 22:37
 * @version: 1.0
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcError error) {
        super(error.getMessage());
    }

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + detail);
    }

    public RpcException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
