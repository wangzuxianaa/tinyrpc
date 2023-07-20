package com.johnchan.rpc.core.netty.server;

import com.johnchan.rpc.core.codec.CommonDecoder;
import com.johnchan.rpc.core.codec.CommonEncoder;
import com.johnchan.rpc.core.serializer.JsonSerializer;
import com.johnchan.rpc.core.server.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.netty.server
 * @className: NettyServer
 * @author: johnchan
 * @description: NIO方式提供服务端
 * @date: 2023/7/20 14:20
 * @version: 1.0
 */

@Slf4j
public class NettyServer implements RpcServer {


    @Override
    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            // 先进行编码
                            channelPipeline.addLast(new CommonEncoder(new JsonSerializer()));
                            channelPipeline.addLast(new CommonDecoder());
                            channelPipeline.addLast(new NettyServerHandle());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("An error occurred while starting the server " + e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
