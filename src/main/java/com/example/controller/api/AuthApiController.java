package com.example.controller.api;

import com.example.service.AuthService;
import com.example.service.VerifyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void Register(@RequestParam("username") String name,
                         @RequestParam("sex") String sex,
                         @RequestParam("grade") String grade,
                         @RequestParam("password") String password,
                         @RequestParam("email") String email,
                         @RequestParam("verify") String verify, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (verifyService.doVerify(email, verify)) {
            service.register(name, sex, grade, password);
            response.sendRedirect("/bookmanager/page/auth/login");
        } else {
            response.sendRedirect("/bookmanager/page/auth/register");
        }
    }


}
