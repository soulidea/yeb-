package com.wang.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.mapper.MenuMapper;
import com.wang.pojo.Admin;
import com.wang.pojo.Menu;
import com.wang.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    //根据用户id获取菜单列表
    @Override
    public List<Menu> getMenuByAdminId() {
        Integer adminId = ((Admin)
                        SecurityContextHolder
                                .getContext()//获取全局上下文
                                .getAuthentication()
                                .getPrincipal()).getId();

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //        从redis获取菜单数据
        List<Menu> menus = (List<Menu>)valueOperations.get("menu_" + adminId);
//        如果为空，去数据库获取
        if(CollectionUtils.isEmpty(menus)){
           menus = menuMapper.getMenusByAdminId(adminId);
           valueOperations.set("menu_"+adminId,menus);
        }
        return menus;


    }

    //根据角色获取菜单列表
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    //查询所有菜单，包括子菜单
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
