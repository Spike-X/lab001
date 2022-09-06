package com.aircraft.codelab.pioneer.controller.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 2022-03-24
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
@Api(tags = "sentinel")
@RequestMapping("/sentinel")
public class SentinelTest {

    @GetMapping("/rateLimiter")
    public String sentinel() {
        try (Entry entry = SphU.entry("batchPay")) {
            /*您的业务逻辑 - 开始*/
            System.out.println("hello world");
            /*您的业务逻辑 - 结束*/
            return "success";
        } catch (BlockException e) {
            /*流控逻辑处理 - 开始*/
            System.out.println("block!");
            /*流控逻辑处理 - 结束*/
            return "try later";
        }
    }

    @PostConstruct
    public void initFlowRules() {
        FlowRule rule = new FlowRule("batchPay");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        rule.setClusterMode(true);

        List<FlowRule> ruleList = new ArrayList<>();
        ruleList.add(rule);
        FlowRuleManager.loadRules(ruleList);
    }
}
