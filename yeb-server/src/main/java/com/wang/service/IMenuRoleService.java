package com.wang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.pojo.MenuRole;
import com.wang.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface IMenuRoleService extends IService<MenuRole> {
//更新角色菜单
    RespBean updateMenuRole(Integer rid, Integer[] mids);

}
