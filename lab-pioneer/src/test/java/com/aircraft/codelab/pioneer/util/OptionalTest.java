package com.aircraft.codelab.pioneer.util;

import com.aircraft.codelab.pioneer.pojo.entity.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * 2022-09-02
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class OptionalTest {
    @Test
    void optionalTest() {
        UserDO userDO = new UserDO();
        userDO.setName("aaa");
        UserDO userDO1 = new UserDO();
        userDO1.setName("bbb");

        // name不为空仍旧产生默认值
        String name = Optional.ofNullable(userDO).map(UserDO::getName).orElse(getUserDO());
        log.debug("name: {}", name);
        // name为空才产生默认值
        String name1 = Optional.ofNullable(userDO1).map(UserDO::getName).orElseGet(this::getUserDO1);
        log.debug("name1: {}", name1);

        String name2 = Optional.ofNullable(userDO1)
                .map(UserDO::getName)
                .filter(n -> n.startsWith("c"))
                .orElseGet(this::getUserDO1)
//                .orElseThrow(() -> new RuntimeException("结果为空！"))
                ;
        log.debug("name2: {}", name2);

        Optional.ofNullable(userDO1).map(UserDO::getName).ifPresent(u -> {
            String upperCase = u.toUpperCase();
            log.debug("upperCase: {}", upperCase);
        });
    }

    private String getUserDO() {
        log.debug("=====>");
        return "mo";
    }

    private String getUserDO1() {
        log.debug("=====>!!!");
        return "ren";
    }
}
