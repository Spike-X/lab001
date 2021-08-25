package com.aircraft.codelab.labcore.mapper;

import com.aircraft.codelab.labcore.pojo.entity.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 2021-08-22
 *
 * @author tao.zhang
 * @since 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
