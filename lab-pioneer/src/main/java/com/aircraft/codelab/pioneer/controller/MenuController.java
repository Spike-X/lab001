package com.aircraft.codelab.pioneer.controller;

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import com.aircraft.codelab.pioneer.pojo.entity.SysMenu;
import com.aircraft.codelab.pioneer.pojo.vo.SysMenuCreatVo;
import com.aircraft.codelab.pioneer.pojo.vo.SysMenuUpdateVo;
import com.aircraft.codelab.pioneer.service.IMenuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public CommonResult<IPage<SysMenu>> query(@RequestParam(defaultValue = "1", required = false)
                                                      int currentPage,
                                              @RequestParam int pageSize,
                                              Integer type) {
        log.debug("currentPage: {},pageSize: {},type: {}", currentPage, pageSize, type);
        IPage<SysMenu> sysMenuPage = iMenuService.query(currentPage, pageSize, type);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), sysMenuPage);
    }

    @ApiOperation(value = "查询菜单")
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<SysMenu> get(@RequestParam String id) {
        log.debug("id: {}", id);
        SysMenu sysMenu = iMenuService.getMenu(id);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), sysMenu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<Long> save(@RequestBody SysMenuCreatVo sysMenuCreatVo) {
        log.debug("save: {}", sysMenuCreatVo);
        Long menuId = iMenuService.addMenu(sysMenuCreatVo);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), menuId);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> update(@RequestBody @Validated(SysMenuUpdateVo.Update.class)
                                          SysMenuUpdateVo sysMenuUpdateVo) {
        log.debug("update: {}", sysMenuUpdateVo);
        iMenuService.updateMenu(sysMenuUpdateVo);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除菜单1", notes = "支持批量删除")
    @DeleteMapping(value = "/delete1", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<Integer> deleteByArray(@RequestParam("idArray") String[] idArray) {
        List<String> list = Arrays.stream(idArray).collect(Collectors.toList());
        log.debug("idList: {}", list);
        // String 有隐式转换问题
        int deleteMenu = iMenuService.deleteMenu(list);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), deleteMenu);
    }

    @ApiOperation(value = "删除菜单2", notes = "支持批量删除")
    @DeleteMapping(value = "/delete2", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<Integer> deleteByParam(@RequestParam("idList") List<String> idList) {
        log.debug("idList: {}", idList);
        // String 有隐式转换问题
        int deleteMenu = iMenuService.deleteMenu(idList);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), deleteMenu);
    }

    // raw application/json ["1428310206666051587","1428310206666051589"]
    @ApiOperation(value = "删除菜单3", notes = "支持批量删除")
    @DeleteMapping(value = "/delete3", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<Integer> deleteByList(@RequestBody List<String> idList) {
        log.debug("idList: {}", idList);
        // String 有隐式转换问题
        int deleteMenu = iMenuService.deleteMenu(idList);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), deleteMenu);
    }
}
