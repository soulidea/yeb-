package com.wang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.pojo.Department;
import com.wang.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface IDepartmentService extends IService<Department> {
//    获取所有部门
    List<Department> getAllDepartments();
//    添加部门
    RespBean addDepartments(Department department);
//    删除部门
    RespBean deleteDepartments(Integer id);
}
