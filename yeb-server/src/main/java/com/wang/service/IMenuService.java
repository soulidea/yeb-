package com.wang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface IMenuService extends IService<Menu> {
    //通过用户id查询菜单列表
    List<Menu> getMenuByAdminId();

//根据角色获取菜单列表
    List<Menu> getMenusWithRole();
//查询所有菜单，包括子菜单
    List<Menu> getAllMenus();

}
