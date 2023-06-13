package com.litecase.boot;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class LitecaseBootApplicationTests {

    //   @Autowired是按类型查找Bean，@Resource是按名查找
    @Resource
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);


        Long count = jdbcTemplate.queryForObject("select count(*) from user", Long.class);

        System.out.println("数量:" + count);

        connection.close();

    }

}
