package com.johnchan.rpc.core.serializer;


public interface CommonSerializer {

    byte[] serialize(Object object);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        // 根据编号分配序列化器
        switch (code) {
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
