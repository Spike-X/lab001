package com.aircraft.codelab.pioneer.mapper;

import com.aircraft.codelab.pioneer.pojo.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<Long> queryMenu(Page<SysMenu> page, Integer type);

    // 待优化
    List<SysMenu> queryMenuById(List<Long> ids);

    Integer deleteBatchByLogic(@Param("updateTime") LocalDateTime updateTime, @Param("idList") List<String> idList);

    Integer deleteBatchByLogicMap(Map<String, Object> map);

    Integer existSubmenu(Long id);
}
