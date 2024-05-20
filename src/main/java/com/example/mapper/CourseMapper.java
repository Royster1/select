package com.example.mapper;


import com.example.entiy.Course;
import com.example.entiy.SelectConnection;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    // 查询课程信息
    @Select("select * from course")
    List<Course> allCourse();

    // 删除课程
    @Delete("delete from course where course_id = #{bid}")
    void deleteCourse(int bid);

    @Insert("insert into course(course_id, course_name, teacher, point, location, limited, is_select)" +
            "values(#{course_id}, #{course_name}, #{teacher}, #{point}, #{location}, #{limited}, 0)")
    void addCourse(@Param("course_id") String course_id, @Param("course_name") String course_name,
                   @Param("teacher")String teacher, @Param("point") int point,
                   @Param("location") String location, @Param("limited") int limited);

    // 获取所有已选课程, 用户借阅后, 就把选课系统对应的课程屏蔽掉
    @Select("select * from elective")
    List<SelectConnection> SelectList(); // 用户借阅列表中没用这本书才能给用户借


    // 预选课程
    @Insert("insert into elective(course_id, uid) values(#{course_id}, #{uid})")
    void addSelect(@Param("course_id") int course_id, @Param("uid") int uid);

    // 退订课程
    @Delete("delete from elective where course_id = #{course_id} and uid = #{uid}")
    void deleteSelect(@Param("course_id") int course_id, @Param("uid") int uid);

    // 获取该课程已选人数
    @Select("select is_select from course where course_id = #{course_id}")
    int getSelectAccount(int course_id);

    // 获取该课程限选人数
    @Select("select limited from course where course_id = #{course_id}")
    int getLimited(int course_id);

    @Select("select * from elective where uid = #{uid}")
    List<SelectConnection> borrowListBySid(Integer sid);

    @Select("SELECT c.*\n" +
            "FROM course c\n" +
            "INNER JOIN elective e ON c.course_id = e.course_id\n" +
            "WHERE e.uid = #{uid};")
    List<Course> selectListByUid(int uid);

    @Select("select * from course where course_id = #{course_id}")
    Course getCourseById(int course_id);

    @Select("SELECT c.*\n" +
            "FROM course c\n" +
            "LEFT JOIN elective e ON c.course_id = e.course_id AND e.uid = #{uid}\n" +
            "WHERE e.course_id IS NULL;")
    List<Course> getUserSelect(int uid);


    // 选课人数+1
    @Update("update course set is_select = is_select + 1 where course_id = #{course_id}")
    void updateIsSelect(int course_id);

    // 选课人数+1
    @Update("update course set is_select = is_select - 1 where course_id = #{course_id}")
    void updateIsSelect_2(int course_id);

    // 课程数量
    @Select("select count(*) from course")
    int getCourseCount();

    // 选课总数
    @Select("select count(*) from elective")
    int getElectiveCount();
}
