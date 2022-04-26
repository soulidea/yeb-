package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface RoleMapper extends BaseMapper<Role> {
//    根据用户id查询角色列表
    List<Role> getRoles(Integer adminId);
}
