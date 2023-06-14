package com.johnchan.test;

import com.johnchan.rpc.core.registry.ServiceRegistry;
import com.johnchan.rpc.core.registry.impl.ServiceRegistryImpl;
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
        ServiceRegistry serviceRegistry = new ServiceRegistryImpl();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
