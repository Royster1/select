package com.example.controller.api;

import com.example.entiy.RestBean;
import com.example.service.AuthService;
import com.example.service.VerifyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    @Resource
    AuthService service;
    @Resource
    VerifyService verifyService;

    @RequestMapping("/verify-code")
    public void verifyCode(@RequestParam("email") String email) throws MessagingException {
            verifyService.sendVerifyCode(email);
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String Register(@RequestParam("username") String name,
                           @RequestParam("sex") String sex,
                           @RequestParam("grade") String grade,
                           @RequestParam("password") String password) {
        service.register(name, sex, grade, password);
        return "redirect:/login";
    }
}
