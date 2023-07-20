package com.johnchan.rpc.common.enumration;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum RpcError {
    SERVICE_INVOCATION_FAILURE("Service call failed"),
    SERVICE_CAN_NOT_BE_NULL("Registered service cannot be empty"),
    SERVICE_NOT_FOUND("Registered service is not found"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("Registered service does not implement the interface"),
    UNKNOWN_PROTOCOL("unknown protocol"),
    UNKNOWN_SERIALIZER("unknown serializer"),
    UNKNOWN_PACKAGE_TYPE("unknown package type");

    private final String message;
}
