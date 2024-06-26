package com.example.service.Impl;

import com.example.entiy.GlobalStat;
import com.example.mapper.CourseMapper;
import com.example.mapper.UserMapper;
import com.example.service.StatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StatServiceImpl implements StatService {

    @Resource
    UserMapper userMapper;

    @Resource
    CourseMapper courseMapper;

    @Override
    public GlobalStat getGlobalStat() {
        return new GlobalStat(userMapper.getStudentCount(),
                courseMapper.getCourseCount(),
                courseMapper.getElectiveCount());
    }

}

