package com.xkx.springboot01.controller;

import com.xkx.springboot01.bean.Department;
import com.xkx.springboot01.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {
    @Autowired
    DeptService deptService;

    @RequestMapping("/dept/{id}")
    public Department getDept(@PathVariable("id") Integer id) {
        return deptService.getDeptById(id);
    }
}
