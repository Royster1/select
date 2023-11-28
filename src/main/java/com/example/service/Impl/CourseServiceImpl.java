package com.example.service.Impl;

import com.example.entiy.Course;
import com.example.mapper.CourseMapper;
import com.example.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    CourseMapper mapper;

    @Override
    public List<Course> getAllCourse() {
        return mapper.allCourse();
    }

    @Override
    public void deleteCourse(int bid) {
        mapper.deleteCourse(bid);
    }

    @Override
    public void addCourse(String course_id, String course_name, String teacher, int point, String location, int limited) {
        mapper.addCourse(course_id, course_name, teacher, point, location, limited);
    }
}
