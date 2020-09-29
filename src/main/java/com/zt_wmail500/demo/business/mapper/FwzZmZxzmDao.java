package com.zt_wmail500.demo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt_wmail500.demo.business.pojo.FwzZmZxzm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FwzZmZxzmDao extends BaseMapper<FwzZmZxzm> {

    FwzZmZxzm selectByPrimaryKey(String id);

    IPage<FwzZmZxzm> query(
            @Param("page") Page<FwzZmZxzm> page,
            @Param("name") String name,
            @Param("xh") String classify);

}