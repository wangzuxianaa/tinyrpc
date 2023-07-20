package com.johnchan.rpc.core.client;


import com.johnchan.rpc.common.entity.RpcRequest;

/*
 * @author johnchan
 * @description 客户端类通用接口
 * @date 2023/7/14 11:51
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

}
