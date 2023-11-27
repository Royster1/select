package com.example.service;

import com.example.entiy.AuthUser;

import javax.servlet.http.HttpSession;

public interface AuthService {
    void register(String name, String sex, String grade, String password);
    AuthUser findUser(HttpSession session);
}
