## Atomikos 与 Spring boot 集成
### 第一步：导入核心的依赖包
、、、
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jta-atomikos</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>${druid.version}</version>
        </dependency>
、、、

### 第二步： 改配置多数据域
配置atomikos 事务管理器，并配置druid作为数据源并且进行监控
application.properties 文件中配置druid的2个数据源。

### 第三步：将配置的数据库连接信息，注入数据源，并且设置druid的监控中心。
MybatisConfiguration 的目的是配置DataSource
AccountDataSourceConfiguration 作用：配置account的数据源的sessionfactory ,同时关联mybaits
RedAccountDataSourceConfiguration 作用：配置RedAccount的数据源的sessionfactory ,同时关联mybaits

### 第四步：service体验 atomikos
PayServiceImpl 作用：模拟下订单的同时扣除，账户余额，红包余额的钱。

### 第五步： junit 测试

### 第六步： 查看Atomikos的日志
1.如何测试atomikos的事务运行结果？
  查看Atomikos的日志，默认情况下，在项目的根目录下会自动创建transaction-logs文件夹，每个Atomikos实例都会有一个全局ID，这个ID为Atomikos运行机器的IP地址；
  这个唯一ID会自动关联多个数据库的事务信息，也就是会关联分支事务id.
  
2.atomikos日志的自定义配置
spring.jta.log-dir=transaction-logs-agan











