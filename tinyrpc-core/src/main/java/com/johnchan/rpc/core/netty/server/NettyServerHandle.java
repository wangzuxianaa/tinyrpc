package com.johnchan.rpc.core.netty.server;

import com.johnchan.rpc.common.entity.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.netty.server
 * @className: NettyServerHandle
 * @author: johnchan
 * @description: NettyServerhandler 用于接收 RpcRequest，并且执行调用，
 *                 将调用结果返回封装成 RpcResponse 发送出去。
 * @date: 2023/7/20 17:25
 * @version: 1.0
 */
@Slf4j
public class NettyServerHandle extends SimpleChannelInboundHandler<RpcRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        log.info("Server received request: {}",  rpcRequest);
        String interfaceName = rpcRequest.getInterfaceName();

    }
}
