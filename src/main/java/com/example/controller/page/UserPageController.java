package com.example.controller.page;

import com.example.entiy.AuthUser;
import com.example.service.AuthService;
import com.example.service.BookService;
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

    @RequestMapping("/index")
    public String index(HttpSession session, Model model){
        model.addAttribute("user", service.findUser(session));
        model.addAttribute("bookList", bookService.getAllBookWithOutBorrow()); // 用户一次只能借阅一本书, 过滤掉了已借阅的书籍
        return "/user/index";
    }

    @RequestMapping("/book")
    public String book(HttpSession session, Model model){
        AuthUser user = service.findUser(session);
        model.addAttribute("user", user);
        model.addAttribute("bookList", bookService.getAllBorrowBookByoId(user.getId()));
        return "/user/book";
    }

}
