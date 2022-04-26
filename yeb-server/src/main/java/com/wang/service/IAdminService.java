package com.wang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.pojo.Admin;
import com.wang.pojo.Menu;
import com.wang.pojo.RespBean;
import com.wang.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登陆之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);
//根据用户名获取用户
    Admin getAdminByUserName(String username);

//    根据用户id查询角色列表
    List<Role> getRoles(Integer adminId);
//    获取所有操作员
    List<Admin> getAllAdmins(String keywords);
//    更新操作员角色
    RespBean updateAdminRole(Integer adminId, Integer[] rids);
//    更新用户密码
    RespBean updateAdminPassword(String oldPass, String pass, Integer adminId);
}
