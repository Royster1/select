package com.example.controller.api;

import com.example.entiy.AuthUser;
import com.example.service.BookService;
import com.example.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/user")
public class UserApiController {

    @Resource
    BookService service;

    @Resource
    CourseService courseService;

    @RequestMapping(value = "/borrow-book", method = RequestMethod.GET)
    public String borrowBook(@RequestParam("id") int course_id,
                             @SessionAttribute("user") AuthUser user){ // sid 从session中取
        courseService.addSelect(course_id, user.getId());
        return "redirect:/page/user/book";
    }

    // 归还图书 将 借阅信息删除
    @RequestMapping(value = "/return-book", method = RequestMethod.GET)
    public String returnBook(@RequestParam("id") int course_id,
                             @SessionAttribute("user")AuthUser user){
        courseService.returnSelect(course_id, user.getId());
        return "redirect:/page/user/book";
    }
}
