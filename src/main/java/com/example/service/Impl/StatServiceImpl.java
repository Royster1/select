package com.example.service.Impl;

import com.example.entiy.GlobalStat;
import com.example.mapper.BookMapper;
import com.example.mapper.UserMapper;
import com.example.service.StatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StatServiceImpl implements StatService {

    @Resource
    UserMapper userMapper;
    @Resource
    BookMapper bookMapper;

    @Override
    public GlobalStat getGlobalStat() {
        return new GlobalStat(userMapper.getStudentCount(),
                bookMapper.getBookCount(),
                bookMapper.getBorrowCount());
    }

}

