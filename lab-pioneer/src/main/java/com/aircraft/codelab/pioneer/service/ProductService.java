package com.aircraft.codelab.pioneer.service;

import java.math.BigDecimal;

/**
 * 2021-11-04
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface ProductService {
    int updatePrice(BigDecimal price, int updateMode);
}
