package com.example.service;


import com.example.entiy.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourse();

    // 删除课程信息
    void deleteCourse(int bid);

    // 添加课程
    void addCourse(String course_id, String course_name,
                    String teacher, int point,
                    String location, int limited);

    // 获取每个课程已选人数
    int getSelectNum(String course_id);

    // 预选课程
    List<Course> getSelect(int id);

    // 添加预选
    void addSelect(int course_id, int id);
    // 查看可预选
    List<Course> getAllCourseWithOutSelect(int uid);
    List<Course> getAllSelectByUid(int id);

    void returnSelect(int course_id, int id);

}
