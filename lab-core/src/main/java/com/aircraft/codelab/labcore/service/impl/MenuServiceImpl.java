package com.aircraft.codelab.labcore.service.impl;

import com.aircraft.codelab.core.util.ValidateUtil;
import com.aircraft.codelab.labcore.mapper.SysMenuMapper;
import com.aircraft.codelab.labcore.pojo.entity.SysMenu;
import com.aircraft.codelab.labcore.pojo.vo.SysMenuCreatVo;
import com.aircraft.codelab.labcore.pojo.vo.SysMenuUpdateVo;
import com.aircraft.codelab.labcore.service.IMenuService;
import com.aircraft.codelab.labcore.service.SysMenuConvert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 2021-08-13
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements IMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Transactional(readOnly = true)
    @Override
    public IPage<SysMenu> query(int pageNum, int pageSize, Integer type) {
        Page<SysMenu> page = new Page<>(pageNum, pageSize);
        // 分页offset过大优化方案
        List<Long> idList = sysMenuMapper.queryMenu(page, type);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<SysMenu> sysMenus = sysMenuMapper.queryMenuById(idList);
            page.setRecords(sysMenus);
        }
        return page;
    }

    @Transactional(readOnly = true)
    @Override
    public SysMenu getMenu(String id) {
        return sysMenuMapper.selectById(id);
    }

    @Transactional
    @Override
    public Long addMenu(SysMenuCreatVo sysMenuCreatVo) {
        ValidateUtil.validate(sysMenuCreatVo, SysMenuCreatVo.Save.class);
        // todo 校验parentId
        SysMenu sysMenuConvert = SysMenuConvert.INSTANCE.vo2do(sysMenuCreatVo);
        SysMenu sysMenu = sysMenuConvert.toBuilder()
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();
        sysMenuMapper.insert(sysMenu);
        return sysMenu.getId();
    }

    @Transactional
    @Override
    public void updateMenu(SysMenuUpdateVo sysMenuUpdateVo) {
        // todo 校验id
        SysMenu sysMenuConvert = SysMenuConvert.INSTANCE.vo2do(sysMenuUpdateVo);
        SysMenu sysMenu = sysMenuConvert.toBuilder().updateTime(LocalDateTime.now()).build();
        sysMenuMapper.updateById(sysMenu);
    }

    @Transactional
    @Override
    public int deleteMenu(List<String> idList) {
        idList.forEach(id -> {
            // todo 校验id
            // 存在子菜单不予删除
            Integer existSubmenu = sysMenuMapper.existSubmenu(Long.parseLong(id));
            if (existSubmenu == 1) {
                throw new RuntimeException("a submenu exists.");
            }
        });
        LocalDateTime now = LocalDateTime.now();
        return sysMenuMapper.deleteBatchByLogic(now, idList);
    }
}
