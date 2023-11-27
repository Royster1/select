package com.example.service.Impl;

import com.example.entiy.AuthUser;
import com.example.mapper.UserMapper;
import com.example.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

// 管理用户的实现类
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    UserMapper mapper;

    @Transactional
    @Override
    public void register(String name, String sex, String grade, String password) {
        // 加密 BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AuthUser user = new AuthUser(0, name, encoder.encode(password), "user");
        if (mapper.registerUser(user) <= 0)
            throw new RuntimeException("用户基本信息添加失败");
        if (mapper.addStudentInfo(user.getId(), name, grade, sex) <= 0 )
            throw new RuntimeException("学生详细信息失败");
    }

    public AuthUser findUser(HttpSession session) {
        AuthUser user = (AuthUser) session.getAttribute("user");
        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user = mapper.getPasswordByUsername(authentication.getName());
            session.setAttribute("user", user);
        }
        return user;
    }
}
