
> Eureka是Netflix开源的一款提供服务注册和发现的产品，它提供了完整的Service Registry和Service Discovery实现。也是springcloud体系中最重要最核心的组件之一。

### 服务中心

服务中心又称注册中心，管理各种服务功能包括服务的注册、发现、熔断、负载、降级等，比如dubbo admin后台的各种功能。

### Eureka

Eureka 是一个基于 REST 的服务，主要在 AWS 云中使用, 定位服务来进行中间层服务器的负载均衡和故障转移。

Spring Cloud 封装了 Netflix 公司开发的 Eureka 模块来实现服务注册和发现。Eureka 采用了 C-S 的设计架构。Eureka Server 作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，使用 Eureka 的客户端连接到 Eureka Server，并维持心跳连接。这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。Spring Cloud 的一些其他模块（比如Zuul）就可以通过 Eureka Server 来发现系统中的其他微服务，并执行相关的逻辑。

Eureka由两个组件组成：Eureka服务器和Eureka客户端。Eureka服务器用作服务注册服务器。Eureka客户端是一个java客户端，用来简化与服务器的交互、作为轮询负载均衡器，并提供服务的故障切换支持。Netflix在其生产环境中使用的是另外的客户端，它提供基于流量、资源利用率以及出错状态的加权负载均衡。

**Eureka的基本架构，由3个角色组成：**

1、Eureka Server

- 提供服务注册和发现

2、Service Provider

- 服务提供方
- 将自身服务注册到Eureka，从而使服务消费方能够找到

3、Service Consumer

- 服务消费方
- 从Eureka获取注册服务列表，从而能够消费服务

### 案例

#### Eureka Server

spring cloud已经帮我实现了服务注册中心，我们只需要很简单的几个步骤就可以完成。

1. 新建一个`SpringBoot`工程，取名为`eureka-server`，`springboot`使用的版本是`2.1.1.RELEASE`

2. 在`pom`中添加依赖：

   ```xml
   <properties>
       <java.version>1.8</java.version>
       <spring-cloud.version>Finchley.RELEASE</spring-cloud.version>
     </properties>
   
   <dependencies>
       <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
       </dependency>
   
       <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-test</artifactId>
         <scope>test</scope>
       </dependency>
     </dependencies>
   
     <dependencyManagement>
       <dependencies>
         <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-dependencies</artifactId>
           <version>${spring-cloud.version}</version>
           <type>pom</type>
           <scope>import</scope>
         </dependency>
       </dependencies>
     </dependencyManagement>
   ```

   **这里`spring-cloud`的版本用的是`Finchley.RELEASE`，spring-boot的版本是`2.1.1.RELEASE`。因此依赖使用的是`spring-cloud-starter-netflix-eureka-server`.**

3. 在启动类中加上`@EnableEurekaServer`注解

   ```java
   @EnableEurekaServer
   @SpringBootApplication
   public class EurekaServerApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(EurekaServerApplication.class, args);
       }
   
   }
   ```

4. 配置文件：

   ```yaml
   server:
     port: 28081
   spring:
     application:
       name: eureka-server
   eureka:
     client:
       fetch-registry: false
       register-with-eureka: false
       service-url:
         defaultZone: http://localhost:${server.port}/eureka/
   ```

   - `eureka.client.register-with-eureka` ：表示是否将自己注册到Eureka Server，默认为true。
   - `eureka.client.fetch-registry` ：表示是否从Eureka Server获取注册信息，默认为true。
   - `eureka.client.serviceUrl.defaultZone` ：设置与Eureka Server交互的地址，查询服务和注册服务都需要依赖这个地址。默认是http://localhost:8761/eureka ；多个地址可使用 , 分隔。

5. 启动工程后，访问：[http://localhost:28081](http://localhost:28081)，可以看到如下页面，其中还没有发现任何服务。

   ![http://localhost:28081](https://github.com/chenanyu/git-img-repository/raw/master/blog/2018/12/16/1.png)

### 集群

注册中心这么关键的服务，如果是单点话，遇到故障就是毁灭性的。在一个分布式系统中，服务注册中心是最重要的基础部分，理应随时处于可以提供服务的状态。为了维持其可用性，使用集群是很好的解决方案。Eureka通过互相注册的方式来实现高可用的部署，所以我们只需要将Eureke Server配置其他可用的serviceUrl就能实现高可用部署。

#### 三节点注册中心

尝试一下三节点的注册中心的搭建。

1. 修改`application.yml`文件：

   ```yaml
   spring:
     profiles:
       active: peer1
   
   ---
   server:
     port: 28081
   spring:
     profiles: peer1
     application:
       name: eureka-server
   eureka:
     instance:
       hostname: peer1
     client:
       fetch-registry: false
       register-with-eureka: false
       service-url:
         defaultZone: http://peer2:28082/eureka/,http://peer3:28083/eureka/
   
   ---
   server:
     port: 28082
   spring:
     profiles: peer2
     application:
       name: eureka-server
   eureka:
     instance:
       hostname: peer2
     client:
       fetch-registry: false
       register-with-eureka: false
       service-url:
         defaultZone: http://peer1:28081/eureka/,http://peer3:28083/eureka/
   
   ---
   server:
     port: 28083
   spring:
     profiles: peer3
     application:
       name: eureka-server
   eureka:
     instance:
       hostname: peer3
     client:
       fetch-registry: false
       register-with-eureka: false
       service-url:
         defaultZone: http://peer2:28082/eureka/,http://peer1:28081/eureka/
   ```

2. 修改本机的`hosts`文件

   ```shell
   sudo vi /etc/hosts
   ```

   添加如下内容：

   ```shell
   127.0.0.1 peer1 peer2 peer3
   ```

   用来模拟不同的环境。

3. 打开`idea`的`Terminal`，依次执行下面命令：

   ```shell
   # 打包
   mvn clean package  -Dmaven.test.skip=true
   
   # 分别以 peer1 和 peer2 配置信息启动 Eureka
   java -jar target/eureka-server-0.0.1-SNAPSHOT.jar  --spring.profiles.active=peer1
   java -jar target/eureka-server-0.0.1-SNAPSHOT.jar  --spring.profiles.active=peer2
   java -jar target/eureka-server-0.0.1-SNAPSHOT.jar  --spring.profiles.active=peer3
   ```

   依次启动完成后，访问[http://peer1:28081](http://peer1:28081)，效果如下：

   ![http://localhost:28081](https://github.com/chenanyu/git-img-repository/raw/master/blog/2018/12/16/2.png)

   ![http://localhost:28081](https://github.com/chenanyu/git-img-repository/raw/master/blog/2018/12/16/3.png)

   到此三节点的配置已经完成。

### 注意事项

- 在搭建 Eureka Server 集群的时候，要把`eureka.client.register-with-eureka`和`eureka.client.fetch-registry`均改为`true`（默认）。否则会出现实例列表为空，且 peer2 不在 available-replicas 而在 unavailable-replicas 的情况（这时其实只是启动了两个单点实例）。

----