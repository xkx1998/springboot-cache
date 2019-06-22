package com.xkx.springboot01;

import com.xkx.springboot01.bean.Employee;
import com.xkx.springboot01.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot01CacheApplicationTests {
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;  //操作k-v都是字符串

    @Autowired
    RedisTemplate redisTemplate;  //操作k-v都是对象

    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;

    /**
     * String(字符串)、List(列表)、Set(集合)、Hash(散列)、zset(有序集合)
     */
    @Test
    public void test01() {
        //给redis中保存数据
//        stringRedisTemplate.opsForValue().append("msg","hello");
//        String msg = stringRedisTemplate.opsForValue().get("msg");
//        System.out.println(msg);
//        stringRedisTemplate.opsForList().leftPush("mylist","1");
//        stringRedisTemplate.opsForList().leftPush("mylist","2");
    }

    @Test
    public void test02() {
        Employee empById = employeeMapper.getEmpById(1);
        //redisTemplate.opsForValue().set("emp-01",empById);
        empRedisTemplate.opsForValue().set("emp-01",empById);

    }
    @Test
    public void contextLoads() {
        Employee empById = employeeMapper.getEmpById(1);
        System.out.println(empById);
    }

}
