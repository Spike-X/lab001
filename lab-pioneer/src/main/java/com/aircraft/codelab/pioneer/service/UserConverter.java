package com.aircraft.codelab.pioneer.service;

import com.aircraft.codelab.pioneer.pojo.entity.UserDO;
import com.aircraft.codelab.pioneer.pojo.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 2021-08-16
 *
 * @author tao.zhang
 * @since 1.0
 */
//@Mapper(builder = @Builder(disableBuilder = true))
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserDO vo2do(UserVo userVO);
}
