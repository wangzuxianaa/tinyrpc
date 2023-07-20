package com.johnchan.rpc.common.enumration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SerializerCode {

    JSON(1);

    private final int Code;
}
