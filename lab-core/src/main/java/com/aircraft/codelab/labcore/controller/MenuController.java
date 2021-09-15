package com.huawei.pcb.transform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huawei.pcb.common.entity.CommonResult;
import com.huawei.pcb.transform.pojo.entity.SysMenu;
import com.huawei.pcb.transform.pojo.vo.SysMenuCreatVo;
import com.huawei.pcb.transform.pojo.vo.SysMenuUpdateVo;
import com.huawei.pcb.transform.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2021-08-11
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
@Api(tags = "菜单")
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService iMenuService;

    @ApiOperation(value = "分页查询所有菜单", notes = "支持条件过滤")
    @GetMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult query(@RequestParam(defaultValue = "1", required = false) int currentPage,
                              @RequestParam int pageSize, Integer type) {
        log.debug("currentPage: {},pageSize: {},type: {}", currentPage, pageSize, type);
        IPage<SysMenu> sysMenuIPage = iMenuService.query(currentPage, pageSize, type);
        return new CommonResult(sysMenuIPage);
    }

    @ApiOperation(value = "查询菜单")
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult get(@RequestParam String id) {
        log.debug("id: {}", id);
        SysMenu sysMenu = iMenuService.getMenu(id);
        return new CommonResult(sysMenu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult save(@RequestBody SysMenuCreatVo sysMenuCreatVo) {
        log.debug("save: {}", sysMenuCreatVo);
        Long menuId = iMenuService.addMenu(sysMenuCreatVo);
        return new CommonResult(menuId);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult update(@RequestBody @Validated(SysMenuUpdateVo.Update.class) SysMenuUpdateVo sysMenuUpdateVo) {
        log.debug("update: {}", sysMenuUpdateVo);
        iMenuService.updateMenu(sysMenuUpdateVo);
        return new CommonResult();
    }

    @ApiOperation(value = "删除菜单", notes = "支持批量删除")
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "idList", value = "多个用,号隔开", dataType = "String", required = true)
    public CommonResult delete(@RequestParam("idList") String[] idList) {
        List<String> list = Arrays.stream(idList).collect(Collectors.toList());
        log.debug("idList: {}", list);
        int deleteMenu = iMenuService.deleteMenu(list);
        return new CommonResult(deleteMenu);
    }
}
