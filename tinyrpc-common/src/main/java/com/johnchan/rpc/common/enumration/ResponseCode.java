package com.johnchan.rpc.common.enumration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    NOT_FOUND_METHOD(500, "未找到指定的方法"),
    NOT_FOUND_CLASS(500, "未找到指定的类");

    private final int code;

    private final String message;
}
