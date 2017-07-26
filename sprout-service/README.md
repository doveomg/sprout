项目结构
    sprout-service
        sprout-service-controller 控制层
            main
                java
                    com.dove.sprout.service.controller 对外提供接口
                resources
                    system 环境配置
                    application.properties 服务配置
        sprout-service-mapper 业务层
            main 
                java
                    com.dove.sprout.service.mapper 业务接口
                    com.dove.sprout.service.mapper.impl 业务实现
        sprout-service-fegin 访问其他服务接口层
            main
                java
                    com.dove.sprout.service.fegin.client fegin 服务定义
                    com.dove.sprout.service.fegin.hystrix 服务熔断、降级等
                    com.dove.sprout.service.fegin.vo 数据传输类
        sprout-service-dao 持久化层
            main
                java
                    com.dove.sprout.service.dao 对应mybatis mapper
                    com.dove.sprout.service.entity 持久化对象
                resources
                    mapping mybatis mapping.xml定义