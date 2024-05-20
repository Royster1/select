package com.example.service.Impl;

import com.example.entiy.Course;
import com.example.mapper.CourseMapper;
import com.example.mapper.UserMapper;
import com.example.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    CourseMapper mapper;

    @Resource
    UserMapper userMapper;

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

    // 退订课程


    @Override
    public List<Course> getSelect(int id) {
        // 通过id查询该学生的sid
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return Collections.emptyList(); // 返回空列表
        return mapper.borrowListBySid(sid) // 拿到学生的借阅信息
                .stream()
                .map(borrow -> {
                    return mapper.getCourseById(borrow.getCourse_id());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addSelect(int course_id, int id) {
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return;
        mapper.addSelect(course_id, id);
    }

    @Override
    public List<Course> getAllCourseWithOutSelect(int uid) {
        return mapper.getUserSelect(uid);
    }


    @Override
    public List<Course> getAllSelectByUid(int id) {
        return mapper.selectListByUid(id);
    }

    @Override
    public void returnSelect(int course_id, int id) {
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return;
        mapper.deleteSelect(course_id, sid);
    }

    @Override
    public void updateIsSelect(int course_id) {
        mapper.updateIsSelect(course_id);
    }

    @Override
    public void updateIsSelect_2(int course_id) {
        mapper.updateIsSelect_2(course_id);
    }
}
