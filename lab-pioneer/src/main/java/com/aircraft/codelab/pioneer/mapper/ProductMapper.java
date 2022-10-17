package com.aircraft.codelab.pioneer.mapper;

import com.aircraft.codelab.pioneer.pojo.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 2021-09-23
 *
 * @author tao.zhang
 * @since 1.0
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
