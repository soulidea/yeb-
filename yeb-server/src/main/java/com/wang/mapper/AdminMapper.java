package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.pojo.Admin;
import com.wang.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
public interface AdminMapper extends BaseMapper<Admin> {
//获取所有操作员
    List<Admin> getAllAdmins(@Param("id") Integer id,@Param("keywords") String keywords);
}
