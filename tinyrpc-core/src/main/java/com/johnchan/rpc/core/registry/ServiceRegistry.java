package com.johnchan.rpc.core.registry;

public interface ServiceRegistry {
    /*
     * @param service:
     * @return void
     * @author johnchan
     * @description 将服务注册到注册表中
     * @date 2023/6/12 20:20
     */
    <T> void register(T service);

    /*
     * @param serviceName:
     * @return Object
     * @author johnchan
     * @description 根据服务的名字来获取相应的服务实体
     * @date 2023/6/12 20:22
     */
    Object getService(String serviceName);
}
