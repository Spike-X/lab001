package com.aircraft.codelab.labcore.mapper;

import com.aircraft.codelab.labcore.pojo.entity.UserDO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2021-08-31
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class MybatisPlusTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void selectPage() {
        Page<UserDO> pageParam = new Page<>(1, 5);
        userMapper.selectPage(pageParam,null);
        List<UserDO> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        pageParam.hasNext();
        pageParam.hasPrevious();
    }
}
