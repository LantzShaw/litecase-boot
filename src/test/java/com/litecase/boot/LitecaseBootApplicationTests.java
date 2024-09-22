package com.litecase.boot;

import com.litecase.boot.web.mapper.ProductMapper;
import com.litecase.boot.web.model.entity.Product;
import com.litecase.boot.web.model.entity.User;
//import com.litecase.boot.web.service.UserService;
import com.litecase.boot.web.service.ProductService;
import com.litecase.boot.web.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class LitecaseBootApplicationTests {

    //   @Autowired是按类型查找Bean，@Resource是按名查找
    @Resource
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RedisTemplate redisTemplate;

//    @Autowired
//    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    UserService userService;

    /**
     * 操作String类型数据
     */
    @Test
    void testString() {
        redisTemplate.opsForValue().set("fruits", "apple");

        String fruits = (String) redisTemplate.opsForValue().get("fruits");

        redisTemplate.opsForValue().set("desc", "lite", 10l, TimeUnit.MILLISECONDS);

        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("fruits", "banana");

        System.out.println(aBoolean);

        System.out.println(fruits);
    }

    /**
     * 操作Hash类型数据
     */
    @Test
    void testHash() {
        HashOperations hashOperations = redisTemplate.opsForHash();

        hashOperations.put("movies", "name", "flash man");
        hashOperations.put("movies", "date", "2023-12-12 12:00:00");
        hashOperations.put("movies", "director", "Lantz Shaw");

        String date = (String) hashOperations.get("movies", "date");

        System.out.println(date);

        Set<String> movies = hashOperations.keys("movies");

        for (String key : movies) {
            System.out.println(key);
        }

        List values = hashOperations.values("movies");

        for (Object value : values) {
            System.out.println(value);
        }
    }

    /**
     * 操作List类型数据
     */
    @Test
    void testList() {
        ListOperations listOperations = redisTemplate.opsForList();

        listOperations.leftPush("books", "a");
        listOperations.leftPushAll("books", "b", "c", "d");

        List<String> books = listOperations.range("books", 0, -1);

        for (String book : books) {
            System.out.println(book);
        }

        // 获取长度
        Long size = listOperations.size("books");
        int tempSize = size.intValue();
    }

    /**
     * 操作Set类型数据
     */
    void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add("mySet", "a", "b", "c");

        Set<String> mySet = setOperations.members("mySet");

        for (String o : mySet) {
            System.out.println(o);
        }

        setOperations.remove("mySet", "a");
    }

    /**
     * 操作Zset类型数据
     */
    void testZset() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add("myZset", "a", 20.0);
        zSetOperations.add("myZset", "c", 30.0);
        zSetOperations.add("myZset", "d", 40.0);

        Set<String> mZset = zSetOperations.range("mZset", 0, -1);

        for (String o : mZset) {
            System.out.println(o);
        }

        zSetOperations.incrementScore("mZset", "a", 60.0);

        zSetOperations.remove("myZset", "a", "d");
    }

    @Test
    void testCommon() {
        // 所有
        Set<String> keys = redisTemplate.keys("*");

        // 是否存在
        Boolean aBoolean = redisTemplate.hasKey("lite");

        // 删除
        redisTemplate.delete("test");

        // 获取数据类型
        redisTemplate.type("user");
    }


    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);


        Long count = jdbcTemplate.queryForObject("select count(*) from user", Long.class);

        System.out.println("数量:" + count);

        connection.close();
    }


    @Test
    void testUserSelect() {
//        User user = userService.findUsername("LantzShaw");

//        System.out.println("user: " + user);
        User user = userService.findUsername("LantzShaw");

        System.out.println("============user===========" + user);
    }

    @Test
    void testUserInsert() {
        User user = new User();

        user.setUsername("Bob");
        user.setPassword(UUID.randomUUID().toString());

        userService.save(user);
    }


    @Test
    void testSelectProduct() {
        System.out.println("--------------Select All Product-----------------");

        List<Product> productList = productMapper.selectList(null);

        for (Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    void testPasswordEncorder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String password = passwordEncoder.encode("123456");

        boolean isMatched = passwordEncoder.matches("123456", password);


        System.out.println("password: " + password + "isMatched" + isMatched);

//        $2a$10$lt9jDjDQ36/BcKHwny6qTetavTVN74.Ezzrme18.vQCCShsBS6uUq
        // $2a$10$zLCLzS3CiWoOidTfyIp5he5bzktOYaT0F2k2zaCS3t7MMWejbcsgK
    }
}
