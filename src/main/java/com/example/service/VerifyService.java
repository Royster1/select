package com.example.service;


import javax.mail.MessagingException;


public interface VerifyService {

    void sendVerifyCode(String mail) throws MessagingException; // 传入邮箱


}
