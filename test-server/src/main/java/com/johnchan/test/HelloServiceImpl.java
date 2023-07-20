package com.johnchan.test;

import com.johnchan.tinyrpc.api.HelloObject;
import com.johnchan.tinyrpc.api.HelloService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class HelloServiceImpl implements HelloService {


    public String hello(HelloObject helloObject) {
        log.info("receive message is {} ", helloObject.getMessage());
        return "return value is " + helloObject.getId();
    }
}
