package com.johnchan.test;

import com.johnchan.tinyrpc.api.HelloObject;
import com.johnchan.tinyrpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @projectName: tinyrpc
 * @package: com.johnchan.test
 * @className: HelloServiceImpl
 * @author: johnchan
 * @description: 接口实现类
 * @date: 2023/6/8 21:47
 * @version: 1.0
 */
public class HelloServiceImpl implements HelloService {
    public static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    public String hello(HelloObject helloObject) {
        logger.info("receive message is {} ", helloObject.getMessage());
        return "return value is " + helloObject.getId();
    }
}
