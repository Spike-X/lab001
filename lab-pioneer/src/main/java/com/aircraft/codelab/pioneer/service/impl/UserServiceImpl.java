package com.aircraft.codelab.pioneer.service.impl;

import com.aircraft.codelab.pioneer.mapper.UserMapper;
import com.aircraft.codelab.pioneer.pojo.entity.UserDO;
import com.aircraft.codelab.pioneer.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 2021-08-22
 *
 * @author tao.zhang
 * @since 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
}
