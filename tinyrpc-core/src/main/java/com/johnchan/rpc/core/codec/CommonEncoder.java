package com.johnchan.rpc.core.codec;

import com.johnchan.rpc.common.entity.RpcRequest;
import com.johnchan.rpc.common.enumration.PackageType;
import com.johnchan.rpc.core.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.codec
 * @className: CommonEncoder
 * @author: johnchan
 * @description: 编码拦截器，将传入的Rpc请求进行编码
 * @date: 2023/7/20 14:37
 * @version: 1.0
 */
public class CommonEncoder extends MessageToByteEncoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer commonSerializer;

    public CommonEncoder(CommonSerializer commonSerializer) {
        this.commonSerializer = commonSerializer;
    }


//    +---------------+---------------+-----------------+-------------+
//    |  Magic Number |  Package Type | Serializer Type | Data Length |
//    |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
//    +---------------+---------------+-----------------+-------------+
//    |                          Data Bytes                           |
//    |                   Length: ${Data Length}                      |
//    +---------------------------------------------------------------+
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(MAGIC_NUMBER);
        if(o instanceof RpcRequest) {
            byteBuf.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            byteBuf.writeInt(PackageType.RESPONSE_PACK.getCode());
        }

        byteBuf.writeInt(commonSerializer.getCode());
        byte[] bytes = commonSerializer.serialize(o);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
