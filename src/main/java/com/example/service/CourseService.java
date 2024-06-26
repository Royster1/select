package com.example.service;


import com.example.entiy.Course;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourse();

    // 删除课程信息
    void deleteCourse(int bid);

    // 添加课程
    void addCourse(String course_id, String course_name,
                    String teacher, int point,
                    String location, int limited);


    // 预选课程
    List<Course> getSelect(int id);

    // 添加预选
    void addSelect(int course_id, int id);
    // 查看可预选
    List<Course> getAllCourseWithOutSelect(int uid);
    List<Course> getAllSelectByUid(int id);

    void returnSelect(int course_id, int id);

    // 选课人数+1
    void updateIsSelect(int course_id);

    // 选课人数-1
    void updateIsSelect_2(int course_id);


}
