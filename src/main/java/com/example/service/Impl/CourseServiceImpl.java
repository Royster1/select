package com.example.service.Impl;

import com.example.entiy.Book;
import com.example.entiy.Borrow;
import com.example.entiy.Course;
import com.example.entiy.SelectConnection;
import com.example.mapper.CourseMapper;
import com.example.mapper.UserMapper;
import com.example.service.CourseService;
import org.apache.ibatis.annotations.Select;
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
    public int getSelectNum(String course_id) {
        return mapper.getSelectAccount(course_id);
    }

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
    public List<Course> getAllCourseWithOutSelect() {
        List<Course> courses = mapper.allCourse();
        List<Integer> selectConnections = mapper.SelectList()
                .stream()
                .map(SelectConnection::getCourse_id)
                .collect(Collectors.toList());
        return courses
                .stream()
                .filter(course -> !selectConnections.contains(Integer.parseInt(course.getCourse_id())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getAllSelectByUid(int id) {
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return Collections.emptyList();
        return mapper.selectListByUid(sid)
                .stream()
                .map(selectConnection -> {
                    return mapper.getCourseById(selectConnection.getCourse_id());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void returnSelect(int course_id, int id) {
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return;
        mapper.deleteSelect(course_id, sid);
    }
}
