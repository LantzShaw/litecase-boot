package com.litecase.boot.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litecase.boot.web.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// NOTE: mapper类（Dao层）
// NOTE: 可以使用@Mapper或者Repository，推荐使用前者，前者是Mybatis的注解，可以直接使用，后者是SpringMVC的注解，若想使用需要额外配置
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getUsers();

    User getUserById(@Param("userId") String userId);

    /**
     * 查询用户名是否存在
     *
     * @param username
     * @return
     */
    User findByUsername(@Param("username") String username);

    void addUser(@Param("User") User user);

    User findUserById(String userId);
}
