package com.example.service.Impl;

import com.example.entiy.Book;
import com.example.entiy.Course;
import com.example.entiy.SelectConnection;
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

    // 获取所有课程, 过滤掉已经给预选了
    @Override
    public List<Course> getAllCourseWithOutSelect() {
        List<Course> books = mapper.allCourse();
        List<Integer> borrows = mapper.SelectList()
                .stream()
                .map(SelectConnection::getCourse_id)
                .collect(Collectors.toList());
        return books.stream().filter(book -> !borrows.contains(book.getCourse_id())).collect(Collectors.toList());
    }

    // 预选课程
    @Override
    public List<Course> getAllSelectByoId(int id) {
        // 通过id查询该学生的sid
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return Collections.emptyList(); // 返回空列表
        return mapper.SelectListBySid(sid) // 拿到学生的借阅信息
                .stream()
                .map(borrow -> {
                    return mapper.getSelectById(borrow.getCourse_id());
                })
                .collect(Collectors.toList());
    }




    // 退订课程
    @Override
    public void returnSelect(int course_id, int id) {
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return;
        mapper.deleteSelect(course_id, sid);
    }

    @Override
    public int getSelectNum(String course_id) {
        return mapper.getSelectAccount(course_id);
    }
}
