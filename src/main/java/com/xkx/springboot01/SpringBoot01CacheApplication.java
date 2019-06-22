package com.xkx.springboot01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 开发中使用缓存中间件：redis,memcached,ehcache;
 *
 *
 * 整合redis作为缓存
 * 1、安装redis，使用docker
 * 2、引入redis的starter
 * 3、配置redis
 * 4、测试缓存
 *    原理：CacheManager===Cache缓存组件来实际给缓存中存取数据
 *    1） 引入redis的starter、容器中保存的是RedisCacheManager
 *    2） RedisCacheManager 帮我们创建RedisCache来作为缓存组件 RedisCache通过操作redis缓存
 *    3） 默认保存数据k-v都是Object；利用序列化保存；如何保存为json
 *           1、引入了redis的starter、cacheManager变为RedisCacheManager
 *           2、默认创建的RedisCacheManger操作redis的时候使用的是RedisTemplate<Object,Object>
 *           3、RedisTemplate<Object,Object>是默认使用JDK序列化机制
 *    4)  自定义CacheManager
 *
 */
@MapperScan("com.xkx.springboot01.mapper")
@SpringBootApplication
@EnableCaching
public class SpringBoot01CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBoot01CacheApplication.class, args);
    }
}
