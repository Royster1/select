package com.example.mapper;


import com.example.entiy.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    // 查询课程信息
    @Select("select * from course")
    List<Course> allCourse();

    // 删除书籍
    @Delete("delete from course where course_id = #{bid}")
    void deleteCourse(int bid);

    @Insert("insert into course(course_id, course_name, teacher, point, location, limited)" +
            "values(#{course_id}, #{course_name}, #{teacher}, #{point}, #{location}, #{limited})")
    void addCourse(@Param("course_id") String course_id, @Param("course_name") String course_name,
                   @Param("teacher")String teacher, @Param("point") int point,
                   @Param("location") String location, @Param("limited") int limited);
}
