package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
//更新角色菜单
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
