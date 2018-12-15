package com.cayzlh.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka的基本架构，由3个角色组成：
 * 1、Eureka Server
 *  提供服务注册和发现
 *
 * 2、Service Provider
 *  服务提供方
 *  将自身服务注册到Eureka，从而使服务消费方能够找到
 *
 * 3、Service Consumer
 *  服务消费方
 *  从Eureka获取注册服务列表，从而能够消费服务
 *
 * 关于配置文件：
 *
 * eureka.client.register-with-eureka ：表示是否将自己注册到Eureka Server，默认为true。
 * eureka.client.fetch-registry ：表示是否从Eureka Server获取注册信息，默认为true。
 * eureka.client.serviceUrl.defaultZone ：设置与Eureka Server交互的地址，查询服务和注册服务都需要依赖这个地址。
 *                                          默认是http://localhost:8761/eureka ；多个地址可使用 , 分隔。
 *
 * @author chenanyu
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}

