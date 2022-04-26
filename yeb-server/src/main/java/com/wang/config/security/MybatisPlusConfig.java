package com.wang.config.security;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
//    分页配置
    @Bean
//    mybatis-plus版本降级,将PaginationInnerInterceptor降级为paginationInterceptor
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
