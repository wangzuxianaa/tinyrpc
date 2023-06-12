package com.johnchan.rpc.core.server;

import com.johnchan.rpc.common.entity.RpcRequest;
import com.johnchan.rpc.common.entity.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.server
 * @className: RpcServer
 * @author: johnchan
 * @description: 远程方法调用的提供者(服务端)
 * @date: 2023/6/7 22:10
 * @version: 1.0
 */
public class RpcServer {
    private final ExecutorService threadPool;

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer() {
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                                            keepAliveTime, TimeUnit.SECONDS,
                                            workingQueue, threadFactory);
    }

    /*
     * @param service:
     * @param port:
     * @return void
     * @author 陈延超
     * @description 服务端注册，监听相应的端口
     * @date 2023/6/7 22:36
     */
    public void register(Object service, int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Rpc Server is Starting....");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                logger.info("Rpc Client has connected,  ip: " + socket.getInetAddress());
                // 创建工作线程
                Socket finalSocket = socket;
                threadPool.execute(() -> {
                    try (ObjectInputStream objectInputStream = new ObjectInputStream(finalSocket.getInputStream());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(finalSocket.getOutputStream())){
                        // 获取客户端请求流，并反射调用响应的方法
                        RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
                        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
                        Object returnObject = method.invoke(service, rpcRequest.getParameters());
                        objectOutputStream.writeObject(RpcResponse.success(returnObject));
                        objectOutputStream.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
                        logger.error("Error: " + e);
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            logger.error("Connect error: " + e);
        }
    }
}
