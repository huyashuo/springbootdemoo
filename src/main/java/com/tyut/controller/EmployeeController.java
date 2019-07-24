package com.tyut.controller;

import com.tyut.dao.DepartmentDao;
import com.tyut.dao.EmployeeDao;
import com.tyut.entities.Department;
import com.tyut.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    //查询所用员工返回员工列表页面
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees=employeeDao.getAll();
        //放在请求域中
        model.addAttribute("emps",employees);
        //thymeleaf默认自动拼串
        //classpath:/templates/***.html
        return "emp/list";
    }

    //员工添加页面
    @GetMapping("/emp")
    public String toAddPage(Model model){
        //来到员工添加页面，查出所有的部门，在页面显示
        Collection<Department> departments=departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee employee){
        System.out.println("保存的员工信息, "+employee);
        //保存员工
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //来到修改页面，查出当前员工，在页面回显
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);
        //页面要显示所有的部门列表
        Collection<Department> departments=departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/modify";
    }

    //员工修改
    @PutMapping("/emp")
    public String updateEmployee(Employee employee){
        System.out.println("修改的员工数据："+employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }
    //员工删除
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }

}
