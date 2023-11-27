package com.example.controller.api;

import com.example.entiy.AuthUser;
import com.example.service.BookService;
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

    @RequestMapping(value = "/borrow-book", method = RequestMethod.GET)
    public String borrowBook(@RequestParam("id") int bid,
                             @SessionAttribute("user") AuthUser user){ // sid 从session中取
        service.borrowBook(bid, user.getId());
        return "redirect:/page/user/book";
    }

    // 归还图书 将 借阅信息删除
    @RequestMapping(value = "/return-book", method = RequestMethod.GET)
    public String returnBook(@RequestParam("id") int bid,
                             @SessionAttribute("user")AuthUser user){
        service.returnBook(bid, user.getId());
        return "redirect:/page/user/book";
    }
}
