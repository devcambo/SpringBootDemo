package com.devcambo.crudmybatis;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(int id);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Insert("INSERT INTO users(name, email) VALUES(#{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE users SET name=#{name}, email=#{email} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id=#{id}")
    void delete(int id);
}