package com.cayzlh.eurekaconsumer.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenanyu
 * @Description HelloRemote
 * @date 2018-12-19.
 */
@FeignClient(name = "eureka-producer")
public interface HelloRemote {

    /**
     * 创建一个 Feign 的客户端接口定义。
     * 使用@FeignClient注解来指定这个接口所要调用的服务名称，
     * 接口中定义的各个函数使用 Spring MVC 的注解就可以来绑定服务提供方的REST 接口，
     *  比如下面就是绑定 eureka-producer 服务的/hello/接口的例子：
     *
     *  此类中的方法和远程服务中 Contoller 中的方法名和参数需保持一致。
     */
    @GetMapping("/hello/")
    String hello(@RequestParam(value = "name") String name);

}
