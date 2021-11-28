package com.aircraft.codelab.labcore.mapper;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.aircraft.codelab.core.util.SnowflakeUtil;
import com.aircraft.codelab.labcore.pojo.entity.UserDO;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 2021-11-21
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class SaveBatchTest {
    @Resource
    private UserMapper userMapper;

    // 记录数量
    private static final int MAX_COUNT = 200000;

    @Test
    void saveBatchByForeach() {
        Snowflake snowflake = SnowflakeUtil.getInstance();
        long id = snowflake.nextId();

        List<UserDO> list = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < MAX_COUNT; i++) {
//            IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
            UserDO user = new UserDO();
//            user.setId(identifierGenerator.nextId(user).longValue());
            user.setName("test:" + i);
            user.setPassword("123456");
            user.setCreateTime(now);
            user.setUpdateTime(now);
            list.add(user);
        }

        long sTime = System.currentTimeMillis(); // 统计开始时间
        // 分片批量插入
        List<List<UserDO>> listPartition = Lists.partition(list, 1000);
        for (List<UserDO> item : listPartition) {
            int i = userMapper.saveBatchByForeach(item);
            System.out.println("新增记录" + i + "条");
        }
        // 批量插入
        long eTime = System.currentTimeMillis(); // 统计结束时间
        System.out.println("执行时间：" + (eTime - sTime));
    }

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void saveBatchType() {
        List<UserDO> list = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < MAX_COUNT; i++) {
            UserDO user = new UserDO();
            user.setName("test:" + i);
            user.setPassword("123456");
            user.setCreateTime(now);
            user.setUpdateTime(now);
            list.add(user);
        }

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            long sTime = System.currentTimeMillis(); // 统计开始时间
            list.forEach(mapper::saveOne);
            sqlSession.commit();
            long eTime = System.currentTimeMillis(); // 统计结束时间
            System.out.println("执行时间：" + (eTime - sTime));
        }
    }
}
