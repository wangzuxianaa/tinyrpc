package com.johnchan.rpc.core.server;

import com.johnchan.rpc.common.entity.RpcRequest;
import com.johnchan.rpc.common.entity.RpcResponse;
import com.johnchan.rpc.core.registry.ServiceRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.server
 * @className: RequestHandleThread
 * @author: johnchan
 * @description:
 * @date: 2023/6/12 23:41
 * @version: 1.0
 */
@Slf4j
@AllArgsConstructor
public class RequestHandlerThread implements Runnable {
    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(rpcRequest, service);
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            log.error("error:", e);
            e.printStackTrace();
        } ;
    }
}
