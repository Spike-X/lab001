package com.aircraft.codelab.labcore.mapper;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.aircraft.codelab.core.util.SnowflakeUtil;
import com.aircraft.codelab.labcore.pojo.entity.UserDO;
import com.aircraft.codelab.labcore.util.MybatisBatchUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@SpringBootTest
public class SaveBatchTest {
    @Resource
    private UserMapper userMapper;

    // 记录数量
    private static final int MAX_COUNT = 100000;

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
        Stopwatch stopwatch = Stopwatch.createStarted();
        // Foreach批量插入 单次插入过多报错：Packet for query is too large
//        int num = userMapper.saveBatchByForeach(list);
//        System.out.println("新增记录" + num + "条");

        // 一个索引列：10W->5.7s 20W->9.4s 50W->29.1s
        List<List<UserDO>> listPartition = Lists.partition(list, 1000);
        for (List<UserDO> item : listPartition) {
            int i = userMapper.saveBatchByForeach(item);
        }
        log.debug("结束：{}", stopwatch.stop());
    }

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void saveBatchByExecutorType() {
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
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            Stopwatch stopwatch = Stopwatch.createStarted();
            // 不加jdbcURl参数rewriteBatchedStatements 效率会奇低
            // 一个索引列：10W->5.2s 20W->9.5s 50W->24.3s
            list.forEach(userMapper::saveOne);
            sqlSession.commit();
            log.debug("结束：{}", stopwatch.stop());
        }
    }

    @Resource
    private MybatisBatchUtil batchUtil;

    @Test
    void BatchTest() {
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
        Stopwatch stopwatch = Stopwatch.createStarted();
        // 一个索引列：10W->6.0s 20W->10.6s
        int num = batchUtil.batchUpdateOrInsert(list, UserMapper.class, (R, userMapper) -> userMapper.saveOne(R));
        log.debug("新增：{}条：耗时：{}", num, stopwatch.stop());
    }
}
