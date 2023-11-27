package com.example.mapper;

import com.example.entiy.AuthUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from users where name = #{username}")
    AuthUser getPasswordByUsername(String username);

    // 事务 users插入失败student表也失效
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("insert into users(name, role, password) values(#{name}, #{role}, #{password})")
    int registerUser(AuthUser user);

    @Insert("insert into student(uid, name, grade, sex) values(#{uid}, #{name}, #{grade}, #{sex})")
    int addStudentInfo(@Param("uid") int uid, @Param("name") String name, @Param("grade") String grade, @Param("sex") String sex);

    @Select("select sid from student where uid = #{uid}") // 通过uid找到sid
    Integer getSidById(int uid);

    // 学生人数
    @Select("select count(*) from student")
    int getStudentCount();
}