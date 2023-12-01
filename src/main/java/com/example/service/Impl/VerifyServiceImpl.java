package com.example.service.Impl;

import com.example.service.VerifyService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Service
public class VerifyServiceImpl implements VerifyService {
    //连接本地的 Redis 服务
    Jedis jedis = new Jedis("localhost", 6379);

    @Override
    public void sendVerifyCode(String mail) throws MessagingException {
        //1 准备步骤
        //1.1 创建一个配置文件保存并读取信息
        Properties properties = new Properties();
        //1.2 设置邮件服务器（这里使用了QQ邮箱）
        properties.setProperty("mail.host","smtp.163.com");
        //1.3 设置发送使用的协议
        properties.setProperty("mail.transport.protocol","smtp");

        //2 连接服务器
        //2.1 创建session会话对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("royster61@163.com", "SOZUOHNHSELFISQR");
            }
        });
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2.2 通过session对象获取Transport对象（需要抛出异常）
        Transport transport = session.getTransport();
        //2.3 连接服务器(需要抛出异常)
        transport.connect("smtp.163.com","royster61@163.com","royster61@163.com");
        //3 发送邮件
        //3.1 创建用来发送邮件的对象(需要传入session)
        MimeMessage mimeMessage = new MimeMessage(session);
        //3.2 创建邮件发送人
        mimeMessage.setFrom(new InternetAddress("royster61@163.com"));
        //3.3 创建邮件接收人(可以同时发送给很多人（添加抄送）)
        mimeMessage.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(mail)});
        //3.4 创建邮件主题
        mimeMessage.setSubject("[广东科技学院] 您的注册码");
        //3.5 创建邮件正文
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;

        // redis
        jedis.set("verify:code:" + mail, code + "");

        mimeMessage.setContent("您的验证码为:"+ code + ", 三分钟内有效, 请及时完成注册! 如果不是本人操作, 请忽略!","text/html;charset=utf-8");
        //3.6 发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        //===================================================================
        //4 关闭连接
        transport.close();
    }


    public boolean doVerify(String email, String code) {
        String string = jedis.get("verify:code:" + email);
        // redis 中 key是 verify:code:royster61@163.com, value是验证码
        jedis.del("verify:code:" + email); // 验证码只能获取一次
        if (string == null) return false; // 邮箱对应不上
        if (!string.equals(code))   return false; // 验证码对应不上
        jedis.del("verify:code:" + email); // 验证码成功只能用一次
        return true; // redis中的code与表单传入过来的code进行对比
    }
}
