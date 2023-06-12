package com.johnchan.test;

import com.johnchan.rpc.core.server.RpcServer;
import com.johnchan.tinyrpc.api.HelloService;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.test
 * @className: TestServer
 * @author: johnchan
 * @description: 服务端测试
 * @date: 2023/6/8 21:46
 * @version: 1.0
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 8090);
    }
}
