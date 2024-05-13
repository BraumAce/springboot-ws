# Nacos 消费者

## 1. 读取项目配置文件、Nacos配置热更新的2种方式

### 1.1 Student

__使用注解 @RefreshScope + @ConfigurationProperties 实现 Nacos 热更新__

`@ConfigurationProperties("student")`中填写配置文件的前缀地址

### 1.2 user (推荐)

__使用注解 @RefreshScope + @Value 实现 Nacos 热更新__

将获取到的配置放在配置类`UserConfig`中，
在控制类`UserController`中通过注解`@Resource`将获取到的配置实例注入，然后在这个类里面去实现业务层的具体逻辑。
非常符合“低耦合”的设计理念。

