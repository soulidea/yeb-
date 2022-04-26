package com.wang.controller;


import com.wang.mapper.AppraiseMapper;
import com.wang.pojo.Appraise;
import com.wang.pojo.RespBean;
import com.wang.service.IAppraiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
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
@RequestMapping("/system/basic/appraise")
public class AppraiseController {
    @Autowired
    private IAppraiseService appraiseService;

    @ApiOperation(value = "显示奖惩规则全部信息")
    @GetMapping("/")
    public List<Appraise> getAllAppraise(){
        return appraiseService.list();
    }

    @ApiOperation(value = "添加奖惩规则信息")
    @PostMapping("/")
    public RespBean saveAppraise(@RequestBody Appraise appraise){
        appraise.setAppDate(LocalDate.now());
        if (appraiseService.save(appraise)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "更新奖惩规则信息")
    @PutMapping("/")
    public RespBean updateAppraise(@RequestBody Appraise appraise){
        if (appraiseService.updateById(appraise)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除奖惩规则信息")
    @DeleteMapping("/{}")
    public RespBean deleteAppraise(@PathVariable Integer id){
        if (appraiseService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除奖惩规则信息")
    @DeleteMapping("/")
    public RespBean deletaAllAppraise(Integer[] ids){
        if (appraiseService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }

}
