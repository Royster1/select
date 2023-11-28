package com.example.controller.page;

import com.example.mapper.CourseMapper;
import com.example.service.AuthService;
import com.example.service.BookService;
import com.example.service.CourseService;
import com.example.service.StatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/page/admin")
public class AdminPageController {

    @Resource
    AuthService service;

    @Resource
    BookService bookService;

    @Resource
    StatService statService;

    @Resource
    CourseService courseService;

    @RequestMapping("/index")
    public String index(HttpSession session, Model model){
        model.addAttribute("user", service.findUser(session));
        model.addAttribute("courseList", courseService.getAllCourse());
        model.addAttribute("stat", statService.getGlobalStat());
        return "/admin/index";
    }

    @RequestMapping("/book")
    public String book(HttpSession session, Model model){
        model.addAttribute("user", service.findUser(session));
        model.addAttribute("courseList", courseService.getAllCourse());
        return "/admin/book";
    }


    @RequestMapping("/add-book")
    public String addBook(HttpSession session, Model model){
        model.addAttribute("user", service.findUser(session));
        return "/admin/add-book";
    }

}
