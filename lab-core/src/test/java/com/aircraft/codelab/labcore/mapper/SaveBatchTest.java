package com.aircraft.codelab.labcore.mapper;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.aircraft.codelab.core.util.SnowflakeUtil;
import com.aircraft.codelab.labcore.pojo.entity.BaseDO;
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
import java.util.stream.Collectors;

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
    private static final int MAX_COUNT = 40000;

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

    /**
     * 批量插入批处理最佳 foreach稍慢
     */
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

    /**
     * 需要关闭 Mybatis 日志打印
     * 5W sql too large
     */
    @Test
    void updateBatch() {
        LocalDateTime now = LocalDateTime.now();
        List<UserDO> userDOS = userMapper.selectAll();
        userDOS.forEach(userDO -> {
            userDO.setPassword("1234567");
            userDO.setUpdateTime(now);
        });
        System.out.println(userDOS.size());
        Stopwatch stopwatch = Stopwatch.createStarted();
        // 5W->18.4s
        List<List<UserDO>> listPartition = Lists.partition(userDOS, 5000);
        for (List<UserDO> item : listPartition) {
            int i = userMapper.updateBatch(item);
        }
        // 5000->2.4s 1W->4.4s
//        int i = userMapper.updateBatch(userDOS);
        log.debug("结束：{}", stopwatch.stop());
    }

    /**
     * 批量更新case when最佳(需分片) 其它很慢
     * 5W sql too large
     */
    @Test
    void updateBatchCase() {
        LocalDateTime now = LocalDateTime.now();
        List<UserDO> userDOS = userMapper.selectAll();
        userDOS.forEach(userDO -> {
            userDO.setPassword("1234567");
            userDO.setUpdateTime(now);
        });
        System.out.println(userDOS.size());
        Stopwatch stopwatch = Stopwatch.createStarted();
        // 5W->5.5s
        List<List<UserDO>> listPartition = Lists.partition(userDOS, 1000);
        for (List<UserDO> item : listPartition) {
            int i = userMapper.updateBatchCase(item);
        }
        // 5000->1.9s 1W->7.5s
//        userMapper.updateBatchCase(userDOS);
        log.debug("结束：{}", stopwatch.stop());
    }

    @Test
    void updateBatchByExecutorType() {
        LocalDateTime now = LocalDateTime.now();
        List<UserDO> userDOS = userMapper.selectAll();
        userDOS.forEach(userDO -> {
            userDO.setPassword("1234567");
            userDO.setUpdateTime(now);
        });
        System.out.println(userDOS.size());

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            Stopwatch stopwatch = Stopwatch.createStarted();
            // 5W->16.6s
            userDOS.forEach(userMapper::updateOne);
            sqlSession.commit();
            log.debug("结束：{}", stopwatch.stop());
        }
    }

    @Test
    void updateBatchById() {
        LocalDateTime now = LocalDateTime.now();
        List<UserDO> userDOS = userMapper.selectAll();
        List<Long> longList = userDOS.stream().map(BaseDO::getId).collect(Collectors.toList());
        List<List<Long>> partition = Lists.partition(longList, 5000);
        Stopwatch stopwatch = Stopwatch.createStarted();
        // 5W->1.6s
        partition.forEach(p -> {
            int batch = userMapper.updateBatchById("admin", now, p);
        });
        log.debug("结束：{}", stopwatch.stop());
        // 5W->1.8s
//        int batch = userMapper.updateBatchById("admin", now, longList);
//        log.debug("修改：{}条：耗时：{}", batch, stopwatch.stop());
    }
}
