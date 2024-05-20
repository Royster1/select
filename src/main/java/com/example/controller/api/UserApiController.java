package com.example.controller.api;

import com.example.entiy.AuthUser;
import com.example.mapper.CourseMapper;
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
    CourseService courseService;

    @Resource
    CourseMapper courseMapper;

    @RequestMapping(value = "/borrow-book", method = RequestMethod.GET)
    public String borrowBook(@RequestParam("id") int course_id,
                             @SessionAttribute("user") AuthUser user){ // sid 从session中取
        if (courseMapper.getSelectAccount(course_id) < courseMapper.getLimited(course_id)){
            courseService.addSelect(course_id, user.getId());
            courseService.updateIsSelect(course_id);
        }
        return "redirect:/page/user/book";
    }

    // 退订课程 将 选课信息删除
    @RequestMapping(value = "/return-book", method = RequestMethod.GET)
    public String returnBook(@RequestParam("id") int course_id,
                             @SessionAttribute("user")AuthUser user){
        courseService.returnSelect(course_id, user.getId());
        courseService.updateIsSelect_2(course_id);
        return "redirect:/page/user/book";
    }
}
