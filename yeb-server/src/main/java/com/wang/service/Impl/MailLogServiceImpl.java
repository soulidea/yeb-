package com.wang.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.mapper.MailLogMapper;
import com.wang.pojo.MailLog;
import com.wang.service.IMailLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wfx
 * @since 2022-04-07
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
