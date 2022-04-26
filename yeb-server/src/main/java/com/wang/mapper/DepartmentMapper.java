package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface DepartmentMapper extends BaseMapper<Department> {
//    获取所有部门
    List<Department> getAllDepartments(Integer parentId);
//    添加部门
    void addDepartments(Department department);
//    删除部门
    void deleteDepartments(Department department);
}
