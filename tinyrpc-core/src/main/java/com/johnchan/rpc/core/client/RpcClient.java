package com.johnchan.rpc.core.client;

import com.johnchan.rpc.common.entity.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.client
 * @className: RpcClient
 * @author: johnchan
 * @description: 远程方法调用的客户端
 * @date: 2023/6/6 23:03
 * @version: 1.0
 */
public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    /*
     * @param rpcRequest:
     * @param host:
     * @param ip:
     * @return Object
     * @author 陈延超
     * @description 向服务端去发送请求
     * @date 2023/6/6 23:06
     */
    public Object sendRequest(RpcRequest rpcRequest, String host, int ip) {
        try (Socket socket = new Socket(host, ip)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用的时候发生错误" + e);
            return null;
        }
    }
}
