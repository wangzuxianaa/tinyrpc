package com.johnchan.rpc.core.server;

import com.johnchan.rpc.common.entity.RpcRequest;
import com.johnchan.rpc.common.entity.RpcResponse;
import com.johnchan.rpc.core.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RpcServer {
    private final ExecutorService threadPool;
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ServiceRegistry serviceRegistry;
    private RequestHandler requestHandler = new RequestHandler();

    public RpcServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                                            KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                                            workingQueue, threadFactory);
    }

    /*
     * @param service:
     * @param port:
     * @return void
     * @author johnchan
     * @description 服务端注册，监听相应的端口
     * @date 2023/6/7 22:36
     */
    public void start(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Rpc Server is Starting....");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                log.info("Rpc Client has connected,  ip: " + socket.getInetAddress());
                // 创建工作线程
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("Server error: " + e);
        }
    }
}
