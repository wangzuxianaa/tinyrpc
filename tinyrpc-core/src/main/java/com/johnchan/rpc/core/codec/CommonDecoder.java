package com.johnchan.rpc.core.codec;

import com.johnchan.rpc.common.entity.RpcRequest;
import com.johnchan.rpc.common.entity.RpcResponse;
import com.johnchan.rpc.common.enumration.PackageType;
import com.johnchan.rpc.common.enumration.RpcError;
import com.johnchan.rpc.common.exception.RpcException;
import com.johnchan.rpc.core.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.codec
 * @className: CommonDecoder
 * @author: johnchan
 * @description: 解码器
 * @date: 2023/7/20 16:47
 * @version: 1.0
 */

@Slf4j
public class CommonDecoder extends ReplayingDecoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 读取魔数
        int magicNum = byteBuf.readInt();
        if(magicNum != MAGIC_NUMBER) {
            log.error("Unknown Protocol Packet: {}", magicNum);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }

        // 读取调用号
        int packageCode = byteBuf.readInt();
        Class<?> packageClass;

        if(packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if(packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else{
            log.error("Unknown Package Type: {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }

        // 读取序列器号
        int serializerCode = byteBuf.readInt();
        CommonSerializer commonSerializer = CommonSerializer.getByCode(serializerCode);
        if(commonSerializer == null) {
            log.error("Unknown serializer: {}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }

        // 读取数据长度
        int dataLength = byteBuf.readInt();
        byte[] bytes = new byte[dataLength];
        // 读取字节数
        byteBuf.readBytes(bytes);
        Object obj = commonSerializer.deserialize(bytes, packageClass);
        list.add(obj);
    }
}
