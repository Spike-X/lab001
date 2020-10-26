package com.zt_wmail500.demo.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt_wmail500.demo.business.mapper.FwzZmZxzmDao;
import com.zt_wmail500.demo.business.pojo.FwzZmZxzm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: demo
 * @description: 数据库测试
 * @author: tao.zhang
 * @create: 2020-07-26 17:42
 **/
@Slf4j
@RestController
@RequestMapping(value = "/test")
public class DatabaseController {

    @Resource
    private FwzZmZxzmDao fwzZmZxzmDao;

    @GetMapping(value = "/queryAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FwzZmZxzm> queryAll() {
        log.info(">>>>>>>>>>>>>>");
        return fwzZmZxzmDao.selectList(null);
    }

    @GetMapping(value = "/queryPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public IPage<FwzZmZxzm> queryPage() {
        log.info(">>>>>>>>>>>>>>");
        IPage<FwzZmZxzm> userPage = new Page<>(2, 5);//参数一是当前页，参数二是每页个数
        return fwzZmZxzmDao.selectPage(userPage, null);
    }

    @GetMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public IPage<FwzZmZxzm> query(String name, String xh) {
        log.info(">>>>>>>>>>>>>>");
        Page<FwzZmZxzm> page = new Page<>(1, 5);//参数一是当前页，参数二是每页个数
        return fwzZmZxzmDao.query(page, name, xh);
    }

}
