package com.example.controller.page;

import com.example.entiy.AuthUser;
import com.example.service.AuthService;
import com.example.service.BookService;
import com.example.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/page/user")
public class UserPageController {

    @Resource
    AuthService service;

    @Resource
    BookService bookService;

    @Resource
    CourseService courseService;

    @RequestMapping("/index")
    public String index(HttpSession session, Model model){
        model.addAttribute("user", service.findUser(session));
        model.addAttribute("bookList", courseService.getAllCourseWithOutSelect());
        return "/user/index";
    }

    @RequestMapping("/book")
    public String book(HttpSession session, Model model){
        AuthUser user = service.findUser(session);
        model.addAttribute("user", user);
        model.addAttribute("bookList", courseService.getAllSelectByUid(user.getId()));
        return "/user/book";
    }
}
