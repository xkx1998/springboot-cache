package com.xkx.springboot01.service;

import com.xkx.springboot01.bean.Employee;
import com.xkx.springboot01.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp")
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存，下一次调用的时候就不用进行数据库查询
     * <p>
     * CacheManager管理多个Cache组件的，对缓存的真正的CRUD组件中，每一个缓存组件有自己唯一一个名字。
     * <p>
     * <p>
     * 原理
     * 1、自动配置类 CacheAutoConfiguration
     * 2、缓存的配置类
     * org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     * 3、哪个配置类默认生效呢 SimpleCacheConfiguration
     * 4、给容器中注册了一个CacheManager:ConcurrentMapManager
     * 5、可以创建和获取ConcurrentMapCache类型的缓存组件，它的作用是将数据保存在ConcurrentMap中
     * <p>
     * 运行流程
     * 1、方法运行之前，先去查询Cache(缓存组件)，按照cacheNames指定的名字获取(CacheManager先获取相应的缓存)
     * 第一次获取缓存，如果没有Cache组件会自动创建。
     * 2、去Cache中查找缓存的内容，使用一个key,默认就是方法的参数。
     * key是按照某种策略生成的：默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key。
     * <p>
     * SimpleKeyGenerator生成key的默认策略：
     * 如果没有参数：key=new SimpleKey();
     * 如果有多个参数：key=new SimpleKey(params);
     * 如果有一个参数：key=参数的值。
     * <p>
     * 3、没有查到缓存，就调用目标方法，
     * 4、将目标方法返回的结果，放进缓存中。
     * <p>
     * 核心：
     * 1) 使用CacheManager[ConcurrentMapCacheManager] 按照名字获取Cache组件[ConcurrentMapCache]组件
     * 2) key使用keyGenerater生成的，默认是SimpleKeyGenerator
     * 3)
     * <p>
     * 几个属性
     * *  cacheNames/value: 指定缓存组件的名字；将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存。
     * *  key:缓存数据时用的key,可以用它来指定，默认是使用方法参数的值  value:方法的返回值
     * key = "#root.methodName+'['+#id+']'"
     * *  编写spEL: #id: 参数id的值   等同于#a0  #p0 #root.args[0]
     * *  keyGenerater: key的生成器：可以自己指定key的生成器的组件id；
     * *       key/keyGenerator
     * *  cacheManager: 指定缓存管理器   或者cacheResolver指定获取缓存解析器。
     * *  condition: 指定符合条件的情况下才缓存；
     * ,condition = "#a0>1" 第一个参数大于0的时候才给缓存
     * *  unless: 否定缓存，当unless指定的条件为true,方法的返回值就不会被缓存。可以获取到结果进行判断
     * *           unless = "result == null"
     * *  sync:是否使用异步模式
     *
     * @param id
     * @return
     */
    @Cacheable(value = {"emp"}/*, keyGenerator = "myKeyGenerator", condition = "#a0>1", unless = "#a0==2"*/)
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * @CachePut: 既调用了方法，又更新缓存数据
     * 修改了数据库的某个数据，同时更新缓存
     * <p>
     * 先调用目标方法，然后将目标方法的结果缓存起来
     */

    @CachePut(value = {"emp"},key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict: 缓存清除
     * key: 指定要清除的数据
     */

    @CacheEvict(value = "emp",key = "#id")
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp: " + id);
        //employeeMapper.deleteEmpById(id);
    }

    @Caching(
            cacheable = {
                    @Cacheable(/*value = "emp",*/key = "#lastName")
            },
            put = {
                    @CachePut(/*value = "emp",*/key = "#result.id"),
                    @CachePut(/*value = "emp",*/key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
