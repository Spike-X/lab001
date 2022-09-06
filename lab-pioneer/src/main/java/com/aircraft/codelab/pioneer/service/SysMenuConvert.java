package com.aircraft.codelab.pioneer.service;

import com.aircraft.codelab.pioneer.pojo.entity.SysMenu;
import com.aircraft.codelab.pioneer.pojo.vo.SysMenuCreatVo;
import com.aircraft.codelab.pioneer.pojo.vo.SysMenuUpdateVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 2021-08-16
 *
 * @author tao.zhang
 * @since 1.0
 */
@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenu vo2do(SysMenuCreatVo sysMenuCreatVo);

    SysMenu vo2do(SysMenuUpdateVo sysMenuUpdateVo);
}
