package com.aircraft.codelab.labcore.mapper;

import com.aircraft.codelab.labcore.pojo.vo.UpdateTaskVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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
