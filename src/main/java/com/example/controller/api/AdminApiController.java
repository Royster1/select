package com.example.controller.api;

import com.example.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/admin")
public class AdminApiController {
    @Resource
    CourseService courseService;

    @RequestMapping(value = "/del-book", method = RequestMethod.GET)
    public String deleteBook(@RequestParam("id") int id) {
        courseService.deleteCourse(id);
        return "redirect:/page/admin/book";
    }

    // 添加书籍
    @RequestMapping(value = "/add-book", method = RequestMethod.POST)
    public String addBook(@RequestParam("course_id") String course_id,
                          @RequestParam("course_name") String course_name,
                          @RequestParam("teacher") String teacher,
                          @RequestParam("point") int point,
                          @RequestParam("location") String location,
                          @RequestParam("limited") int limited) {
        courseService.addCourse(course_id, course_name, teacher, point, location, limited);
        return "redirect:/page/admin/book";
    }
}
