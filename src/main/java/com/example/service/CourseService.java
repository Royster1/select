package com.example.service;


import com.example.entiy.Book;
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


    // 获取所有课程, 过滤掉已经给预选了
    List<Course> getAllCourseWithOutSelect();

    // 预选课程
    List<Course> getAllSelectByoId(int id); // uid

    // 退订课程
    void returnSelect(int course_id, int id);

    // 获取每个课程已选人数
    int getSelectNum(String course_id);


}
