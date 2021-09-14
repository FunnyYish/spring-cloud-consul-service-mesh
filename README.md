# 用途

用于扩展Spring Cloud consul，使其能支持consul mesh。主要扩充了一下特性：
- 在服务注册时附加注册sidecar信息。
- 服务调用时将通信链路改至127.0.0.1并且端口指向配置文件中指定的上游微服务端口

## 使用方式

需要先本地mvn install。仅在spring-cloud 2020.0.3、spring boot >2.4.10下测试过。demo参见https://github.com/FunnyYish/consul-service-mesh-demo