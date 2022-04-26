package com.wang.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.wang.pojo.*;
import com.wang.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
@RestController
@RequestMapping("/system/basic/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IDepartmentService departmentService;


    @ApiOperation("查询所有员工（分页）")
    @GetMapping("/")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "9") Integer size,
                                    Employee employee,
                                    LocalDate[] beginDateScope){
        return employeeService.getEmployeeByPage(currentPage,size,employee,beginDateScope);
    }

    @ApiOperation("查询所有政治面貌")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }

    @ApiOperation("获取所有职称")
    @GetMapping("/joblevel")
    public List<Joblevel> getAllJobLevels(){
        return joblevelService.list();
    }

    @ApiOperation("获取所有民族")
    @GetMapping("/nation")
    public List<Nation> getAllNations(){
        return nationService.list();
    }

    @ApiOperation("获取所有职位")
    @GetMapping("/position")
    public List<Position> getAllPositions(){
        return positionService.list();
    }

    @ApiOperation("获取所有部门")
    @GetMapping("/deps")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @ApiOperation("获取工号")
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID(){
        return employeeService.maxWorkID();
    }

    @ApiOperation("添加员工")
    @PostMapping("/")
    public RespBean addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @ApiOperation("更新员工")
    @PutMapping("/")
    public RespBean updateEmployee(@RequestBody Employee employee){
        if(employeeService.updateById(employee)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation("shanchu员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployee(@PathVariable Integer id){
        if(employeeService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation("导出员工数据")
    @GetMapping(value = "/export",produces = "application/octet-stream" )
    public void exportEmployee(HttpServletResponse response){
        Collection<Employee> list = employeeService.getEmployee(null);
        ExportParams params=new ExportParams("员工表","员工表", ExcelType.HSSF);//文件名，工作区名，2003版本
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream outputStream=null;
        try {
//            流形式传输数据
            response.setHeader("content-type",
                    "application/octet-stream");
//            防止中文乱码
            response.setHeader("content-disposition",
                    "attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
           outputStream = response.getOutputStream();
           workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public RespBean importEmployee(@RequestPart MultipartFile file){
        ImportParams params=new ImportParams();
//            去掉标题行
        params.setTitleRows(1);
        List<Nation> nationList=nationService.list();
        List<Department> departmentList = departmentService.list();
        List<Joblevel> joblevelList = joblevelService.list();
        List<Position> positionList = positionService.list();
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);

            list.forEach(employee -> {
                employee.setNationId(nationList.get(
//                        民族id
                        nationList.indexOf(
                                new Nation(
                                        employee.getNation().getName()
                                )
                        )
                ).getId());

                employee.setDepartmentId(departmentList.get(
//                        部门id
                        departmentList.indexOf(
                                new Department(
                                        employee.getDepartment().getName()
                                )
                        )
                ).getId());

                employee.setJobLevelId(joblevelList.get(
//                        职称id
                        joblevelList.indexOf(
                                new Joblevel(
                                        employee.getJoblevel().getName()
                                )
                        )
                ).getId());

                employee.setPosId(positionList.get(
//                        职位id
                        positionList.indexOf(
                                new Position(
                                        employee.getPosition().getName()
                                )
                        )
                ).getId());

                employee.setPoliticId(politicsStatusList.get(
//                        民政治面貌id
                        politicsStatusList.indexOf(
                                new PoliticsStatus(
                                        employee.getPoliticsStatus().getName()
                                )
                        )
                ).getId());
            });
            if (employeeService.saveBatch(list)){
                return RespBean.success("导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败");

    }
}
