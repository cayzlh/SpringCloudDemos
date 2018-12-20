package com.cayzlh.eurekaconsumer.controller;

import com.cayzlh.eurekaconsumer.remote.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过 Spring Cloud Feign 来实现服务调用的方式非常简单， 通过@FeignClient定义的接口来统一的声明我们需要依赖的微服务接口。
 * 而在具体使用的时候就跟调用本地方法一点的进行调用即可。 由于Feign 是基于 Ribbon 实现的，所以它自带了客户端负载均衡功能，
 * 也可以通过 Ribbon 的 IRule 进行策略扩展。
 * 另外，Feign 还整合的 Hystrix 来实现服务的容错保护(在 Finchley.RC1 版本中，Feign 的 Hystrix 默认是关闭的)
 *
 * 没加入 eureka-client 这个依赖，只加了 spring-boot-starter-web 和 spring-cloud-starter-openfeign。
 * 只有后两者的话，启动的时候其实是不会有任何异常被抛出的， 但是如果细心地查看了启动 log 的话，
 * 其中有这么一条可以看出实际上确实是没有获取到任何服务的:
 * c.netflix.loadbalancer.BaseLoadBalancer  : Client: eureka-producer instantiated a LoadBalancer:
 * DynamicServerListLoadBalancer:{NFLoadBalancer:name=eureka-producer,current list of
 * Servers=[],Load balancer stats=Zone stats: {},Server stats: []}ServerList:null
 *
 * 要想使用 Feign，至少需要以下三个依赖:
 *
 * spring-boot-starter-web
 * spring-cloud-starter-openfeign
 * spring-cloud-starter-netflix-eureka-client
 *
 * @author chenanyu
 * @Description HelloController
 * @date 2018-12-19.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    HelloRemote helloRemote;

    @GetMapping("/{name}")
    public String index(@PathVariable("name") String name) {
        return helloRemote.hello(name + "!");
    }

}

