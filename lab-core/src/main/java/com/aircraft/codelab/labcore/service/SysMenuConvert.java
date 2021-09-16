package com.aircraft.codelab.labcore.service;

import com.aircraft.codelab.labcore.pojo.entity.SysMenu;
import com.aircraft.codelab.labcore.pojo.vo.SysMenuCreatVo;
import com.aircraft.codelab.labcore.pojo.vo.SysMenuUpdateVo;
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
