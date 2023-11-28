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
}
