package com.cayzlh.eurekaconsumer.hystrix;

import com.cayzlh.eurekaconsumer.remote.HelloRemote;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description HelloRemote的熔断回调方法类
 *
 * 需要在接口定义的地方设置fallback属性
 *
 * @author chenanyu
 * @date 2018-12-22.
 */
@Component
public class HelloRemoteHystrix implements HelloRemote {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "Hello，"+name+" ，当前服务崩了。";
    }
}
