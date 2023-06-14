package com.johnchan.test;

import com.johnchan.rpc.core.client.RpcClientProxy;
import com.johnchan.tinyrpc.api.HelloObject;
import com.johnchan.tinyrpc.api.HelloService;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.test
 * @className: TestClient
 * @author: johnchan
 * @description: 测试客户端
 * @date: 2023/6/8 21:35
 * @version: 1.0
 */
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(3, "I am your Dad");
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}
