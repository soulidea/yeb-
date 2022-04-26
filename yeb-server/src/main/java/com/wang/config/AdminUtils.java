package com.wang.config;

import com.wang.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtils {
//    获取当前登录操作员
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
}
