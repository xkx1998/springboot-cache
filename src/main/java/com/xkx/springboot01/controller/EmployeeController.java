package com.xkx.springboot01.controller;

import com.xkx.springboot01.bean.Employee;
import com.xkx.springboot01.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @ResponseBody
    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable Integer id) {
        Employee emp = employeeService.getEmp(id);
        return emp;
    }

    @GetMapping("/emp")
    public Employee updateEmployee(Employee employee) {
        employeeService.updateEmp(employee);
        return employee;
    }

    @GetMapping("/delemp")
    public String deleteEmployee(Integer id) {
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/lastname/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName) {
        return employeeService.getEmpByLastName(lastName);
    }
}
