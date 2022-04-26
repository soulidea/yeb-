package com.wang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.pojo.Employee;
import com.wang.pojo.RespBean;
import com.wang.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface IEmployeeService extends IService<Employee> {
//    查询所有员工（分页）
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);
//    获取最大工号
    RespBean maxWorkID();
//    添加员工
    RespBean addEmployee(Employee employee);
//    查询员工
    List<Employee> getEmployee(Integer id);
//    获取所有员工的账套
    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
