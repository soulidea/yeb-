package com.wang.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.mapper.DepartmentMapper;
import com.wang.pojo.Department;
import com.wang.pojo.RespBean;
import com.wang.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    //    获取所有部门
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }
    //    添加部门
    @Override
    public RespBean addDepartments(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartments(department);
        if(department.getResult()==1){
           return RespBean.success("添加成功",department);
        }
        return RespBean.error("添加失败");
    }
//    删除部门
    @Override
    public RespBean deleteDepartments(Integer id) {
        Department department=new Department();
        department.setId(id);
        departmentMapper.deleteDepartments(department);
        if (department.getResult()==-2){
            return RespBean.error("该部门下还有子部门，删除失败");
        }
        if(department.getResult()==-1){
            return RespBean.error("该部门下还有员工，删除失败");
        }
        if(department.getResult()==1){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
