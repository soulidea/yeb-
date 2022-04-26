package com.wang.config.security.component;

import com.wang.pojo.Menu;
import com.wang.pojo.Role;
import com.wang.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求url分析请求所需的角色
 *
 */
@Component
@Slf4j
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

   @Autowired
   private IMenuService menuService;

   AntPathMatcher antPathMatcher=new AntPathMatcher();


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

//        获取请求的url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.getMenusWithRole();
        for (Menu menu : menus) {
//            判断请求url与菜单角色是否匹配
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                log.info("{}",menu.getRoles().stream().map(Role::getName));
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                log.info("{}",menu.getRoles().stream().map(Role::getName).toArray(String[]::new));
                return SecurityConfig.createList(str);

            }
        }
//        没匹配的url默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
