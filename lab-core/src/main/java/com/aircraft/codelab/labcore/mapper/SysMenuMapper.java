package com.aircraft.codelab.labcore.mapper;

import com.aircraft.codelab.labcore.pojo.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<Long> queryMenu(Page<SysMenu> page, Integer type);

    List<SysMenu> queryMenuById(List<Long> ids);

    Integer deleteBatchByLogic(@Param("updateTime") LocalDateTime updateTime, @Param("idList") List<String> idList);

    Integer existSubmenu(Long id);
}
