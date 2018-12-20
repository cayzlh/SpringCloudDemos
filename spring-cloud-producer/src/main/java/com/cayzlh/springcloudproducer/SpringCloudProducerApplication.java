package com.cayzlh.springcloudproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 保持默认生成的即可， Finchley.RC1 这个版本的 Spring Cloud 已经无需添加@EnableDiscoveryClient注解了。
 * （那么如果我引入了相关的 jar包又想禁用服务注册与发现怎么办？设置eureka.client.enabled=false）
 *
 * @author chenanyu
 */
@SpringBootApplication
public class SpringCloudProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProducerApplication.class, args);
    }

}

