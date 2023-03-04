package com.aircraft.codelab.pioneer.mapper;

import cn.hutool.core.lang.Snowflake;
import com.aircraft.codelab.core.util.SnowflakeUtil;
import com.aircraft.codelab.pioneer.pojo.entity.LoanContract;
import com.aircraft.codelab.pioneer.pojo.vo.UpdateTaskVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2022-07-10
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@SpringBootTest
public class MybatisTest {
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private LoanContractMapper loanContractMapper;

    @Test
    void batchSave() {
        List<LoanContract> list = Lists.newArrayList();
        LoanContract loanContract1 = new LoanContract();
//        loanContract1.setContractNo("111");
        loanContract1.setContractNo("444");
        LoanContract loanContract2 = new LoanContract();
//        loanContract2.setContractNo("222");
        loanContract2.setContractNo("555");
        LoanContract loanContract3 = new LoanContract();
//        loanContract3.setContractNo("333");
        loanContract3.setContractNo("111");
        list.add(loanContract1);
        list.add(loanContract2);
        list.add(loanContract3);
        Snowflake snowflake = SnowflakeUtil.getInstance();
        LocalDateTime now = LocalDateTime.now();
        for (LoanContract loanContract : list) {
            loanContract.setId(snowflake.nextId());
            loanContract.setLoanAmount(new BigDecimal("100"));
            loanContract.setCreateTime(now);
            loanContract.setUpdateTime(now);
        }
        try {
            loanContractMapper.insertBatch(list);
        } catch (DuplicateKeyException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    void MapTest() {
        Map<String, Object> map = new HashMap<>(2);
        LocalDateTime now = LocalDateTime.now();
        List<String> list = new ArrayList<>();
        list.add("1428310206666051611");
        list.add("1428310206666051613");
        map.put("updateTime", now);
        map.put("idList", list);
        Integer integer = sysMenuMapper.deleteBatchByLogicMap(map);
        log.debug("成功：{}", integer);
    }

    @Test
    void updateByVo() {
        LocalDateTime now = LocalDateTime.now();
        UpdateTaskVo updateTaskVo = new UpdateTaskVo();
        updateTaskVo.setId(1432657379465654L);
        updateTaskVo.setProductName("车享贷007");
        updateTaskVo.setUpdateTime(now);
        loanContractMapper.updateContractState(updateTaskVo);
    }

    @Test
    void updateBatch() {
        LocalDateTime now = LocalDateTime.now();
        UpdateTaskVo updateTaskVo = new UpdateTaskVo();
        updateTaskVo.setId(1432657379465654L);
        updateTaskVo.setProductName("车享贷007");
        updateTaskVo.setUpdateTime(now);

        UpdateTaskVo updateTaskVo1 = new UpdateTaskVo();
        updateTaskVo1.setId(1432657927816376L);
        updateTaskVo1.setProductName("车享贷008");
        updateTaskVo1.setUpdateTime(now);

        List<UpdateTaskVo> updateTaskVos = new ArrayList<>();
        updateTaskVos.add(updateTaskVo);
        updateTaskVos.add(updateTaskVo1);
        // jdbc添加参数allowMultiQueries=true
        int batch = loanContractMapper.updateBatch(updateTaskVos);
        // 返回一条更新记录
        log.debug("成功更新数量：{}", batch);
    }

    @Test
    void batchUpdateCaseWhen() {
        LocalDateTime now = LocalDateTime.now();
        UpdateTaskVo updateTaskVo = new UpdateTaskVo();
        updateTaskVo.setId(1432657379465654L);
        updateTaskVo.setProductName("车享贷007");
        updateTaskVo.setUpdateTime(now);

        UpdateTaskVo updateTaskVo1 = new UpdateTaskVo();
        updateTaskVo1.setId(1432657927816376L);
        updateTaskVo1.setProductName("车享贷008");
        updateTaskVo1.setUpdateTime(now);

        List<UpdateTaskVo> updateTaskVos = new ArrayList<>();
        updateTaskVos.add(updateTaskVo);
        updateTaskVos.add(updateTaskVo1);
        int i = loanContractMapper.batchUpdateCaseWhen(updateTaskVos);
        // 返回实际更新记录数
        log.debug("成功更新数量：{}", i);
    }
}
