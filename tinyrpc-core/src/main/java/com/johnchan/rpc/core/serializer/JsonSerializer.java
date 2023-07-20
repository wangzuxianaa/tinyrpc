package com.johnchan.rpc.core.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnchan.rpc.common.entity.RpcRequest;
import com.johnchan.rpc.common.enumration.SerializerCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.serializer
 * @className: JsonSerializer
 * @author: johnchan
 * @description: Json序列化器
 * @date: 2023/7/20 15:56
 * @version: 1.0
 */

@Slf4j
public class JsonSerializer implements CommonSerializer{

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("serialize error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object object = objectMapper.readValue(bytes, clazz);
            if(object instanceof RpcRequest) {
                object = handleRequest(object);
            }
            return object;
        } catch (IOException e) {
            log.error("deserialize error:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Object handleRequest(Object object) throws IOException {
        RpcRequest rpcRequest = (RpcRequest) object;

        for(int i = 0; i < rpcRequest.getParamTypes().length; i++) {
            Class<?> clazz = rpcRequest.getParamTypes()[i];
            if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())) {
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.JSON.getCode();
    }
}
