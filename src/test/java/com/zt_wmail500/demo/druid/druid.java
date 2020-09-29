package com.zt_wmail500.demo.druid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @program: demo
 * @description: druid测试
 * @author: tao.zhang
 * @create: 2020-07-27 13:17
 **/
@DisplayName("连接池测试")
@SpringBootTest
public class druid {
    @Resource
    private DataSource dataSource;

    @Test
    public void testConnection() throws Exception{
        System.out.println(dataSource.getClass());
        Connection connection = this.dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
}
