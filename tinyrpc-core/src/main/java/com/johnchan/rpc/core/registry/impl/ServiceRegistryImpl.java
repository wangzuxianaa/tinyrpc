package com.johnchan.rpc.core.registry.impl;

import com.johnchan.rpc.common.enumration.RpcError;
import com.johnchan.rpc.common.exception.RpcException;
import com.johnchan.rpc.core.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.rpc.core.registry.impl
 * @className: ServiceRegistryImpl
 * @author: johnchan
 * @description: 服务注册功能的接口实现类
 * @date: 2023/6/12 20:28
 * @version: 1.0
 */
@Slf4j
public class ServiceRegistryImpl implements ServiceRegistry {
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void register(T service) {
        String serviceName = service.getClass().getName();
        if(registeredService.contains(serviceName)) {
            return;
        }
        registeredService.add(serviceName);
        Class<?>[] serviceInterfaces = service.getClass().getInterfaces();

        if(serviceInterfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }

        for(Class<?> i : serviceInterfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }

        log.info("To Interface：{} register service：{}", serviceInterfaces, serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
