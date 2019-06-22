package com.xkx.springboot01.service;

import com.xkx.springboot01.bean.Department;
import com.xkx.springboot01.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class DeptService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("myCacheManager")
    @Autowired
    RedisCacheManager redisCacheManager;

    /*@Cacheable(cacheNames = "dept", key = "#root.args[0]")*/
    public Department getDeptById(Integer id) {
        System.out.println("查询部门：" + id);
        Department department = departmentMapper.getDeptById(id);
        //用编码的方式缓存
        Cache cacheManager = redisCacheManager.getCache("dept");

        cacheManager.put("dept:1",department);
        return department;
    }
}
