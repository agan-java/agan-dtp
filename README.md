

## Atomikos与SpringBoot集成
### 第一步：导入核心的依赖包
```
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-jta-atomikos</artifactId>
      </dependency>
      <dependency>
          <groupId>com.alibaba</groupId>
          <artifactId>druid</artifactId>
          <version>${druid.version}</version>
      </dependency>
        
```

### 第二步：改配置多数据源
配置atomikos事务管理器，并配置druid作为数据源并且进行监控
application.properties 文件中配置druid的2个数据库。

### 第三步：将配置的数据库连接信息，注入数据源，并且设置durid监控中心
MybatisConfiguration 的目的是配置 DataSource
AccountDataSourceConfiguration 作用：配置account的数据源的sessionfactory，同时关联mybaits
RedAccountDataSourceConfiguration 作用：配置RedAccount的数据源的sessionfactory，同时关联mybaits
 
### 第四步：service体验 atomikos
PayServiceImplde 作用：模拟下订单的同时扣除，账户余额、红包余额的钱。

### 第五步：junit测试

### 第六步：查看atomikos日志
1.如何测试atomikos的事务运行结果？  
   查看Atomikos的日志，默认情况下，每个Atomikos实例有一个唯一的ID，这个ID为Atomikos运行的机器的IP地址；这个唯一ID包含了多个数据源的事务信息。

2.atomikos日志的自定义配置  
spring.jta.log-dir=transaction-logs-agan  
spring.jta.transaction-manager-id=txManager

