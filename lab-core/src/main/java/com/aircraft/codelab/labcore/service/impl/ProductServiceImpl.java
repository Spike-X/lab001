package com.aircraft.codelab.labcore.service.impl;

import com.aircraft.codelab.core.exception.ApiException;
import com.aircraft.codelab.labcore.mapper.ProductMapper;
import com.aircraft.codelab.labcore.pojo.entity.Product;
import com.aircraft.codelab.labcore.service.ProductService;
import com.aircraft.codelab.labcore.enums.UpdateEnum;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 2021-11-04
 *
 * @author tao.zhang
 * @since 1.0
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Retry(name = "backendB")
    @Transactional
    @Override
    public int updatePrice(BigDecimal price, int updateMode) {
        BigDecimal difference = null;
        if (updateMode == UpdateEnum.PRICE_ADD.getCode()) {
            difference = price.multiply(BigDecimal.valueOf(UpdateEnum.PRICE_ADD.getCode()));
        } else if ((updateMode == UpdateEnum.PRICE_SUB.getCode())) {
            difference = price.multiply(BigDecimal.valueOf(UpdateEnum.PRICE_SUB.getCode()));
        } else {
            log.error("param error");
        }
        Product product = productMapper.selectById(1L);
        product.setAccountBalance(product.getAccountBalance().add(difference));
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int row = productMapper.updateById(product);
        log.debug("row: {}", row);
        if (row == 0) {
            throw new ApiException("failed,concurrent error");
        }
        return row;
    }
}
