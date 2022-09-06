package com.aircraft.codelab.pioneer.mapper;

import com.aircraft.codelab.pioneer.pojo.entity.UserDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
        Page<UserDO> page = new Page<>(1, 5);
        IPage<UserDO> userIPage = userMapper.selectPage(page, null);
        List<UserDO> recordsList = userIPage.getRecords();
        List<UserDO> records = page.getRecords();
        long total = page.getTotal();
        page.hasNext();
        page.hasPrevious();
    }
}
