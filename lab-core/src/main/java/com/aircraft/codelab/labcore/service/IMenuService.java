package com.aircraft.codelab.labcore.service;

import com.aircraft.codelab.labcore.pojo.entity.SysMenu;
import com.aircraft.codelab.labcore.pojo.vo.SysMenuCreatVo;
import com.aircraft.codelab.labcore.pojo.vo.SysMenuUpdateVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 2021-08-11
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface IMenuService extends IService<SysMenu> {
    IPage<SysMenu> query(
            @NotNull(message = "当前页不能为空")
            @Max(value = 100000, message = "当前页取值范围1-99999")
            @Min(value = 1, message = "最小值大于等于1")
                    int pageNum,
            @NotNull(message = "每页页数不能为空")
            @Max(value = 100, message = "每页页数取值范围1-99")
            @Min(value = 1, message = "最小值大于等于1")
                    int pageSize,
            Integer type);

    Long addMenu(SysMenuCreatVo sysMenuCreatVo);

    SysMenu getMenu(String id);

    void updateMenu(SysMenuUpdateVo sysMenuUpdateVo);

    int deleteMenu(List<String> idList);
}
