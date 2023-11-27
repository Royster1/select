package com.example.config;

import com.example.entiy.AuthUser;
import com.example.mapper.UserMapper;
import com.example.service.Impl.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

// 创建一个配置类用于配置SpringSecurity

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //继承WebSecurityConfigurerAdapter，之后会进行配置

    @Resource
    UserMapper mapper;

    // 处理用户登陆
    @Resource
    UserAuthService service;

    // 数据库储存cookie, 记住我 通过数据库实现
    @Resource
    PersistentTokenRepository repository;

    @Bean
    public PersistentTokenRepository jdbcRepository(@Autowired DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();  //使用基于JDBC的实现
        repository.setDataSource(dataSource);   //配置数据源
        // 第一次启动 将会创建一张表 创建完成后 把下面这一行删掉 不然启动不了
        // repository.setCreateTableOnStartup(true);   //启动时自动创建用于存储Token的表（建议第一次启动之后删除该行）
        return repository;
    }

    // 修改Security默认的页面
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()   //首先需要配置哪些请求会被拦截，哪些请求必须具有什么角色才能访问
                .antMatchers("/static/**", "/page/auth/**", "/page/auth", "/api/auth/**").permitAll()    //静态资源，使用permitAll来运行任何人访问（注意一定要放在前面） permitAll()允许登录页面所有人都可以访问
                .antMatchers("/page/user/**", "/api/user/**").hasRole("user") // user 只能访问 user页面
                .antMatchers("/page/admin/**", "/api/admin/**").hasRole("admin") // admin 只能访问admin页面
                .anyRequest().hasAnyRole("user", "admin")
                /* 不用这样写了, 用注解写
                .antMatchers("/index").hasAnyRole("user", "admin")    //所有请求必须登陆并且是user角色才可以访问（不包含上面的静态资源）
                .anyRequest().hasRole("admin") // 任意地址admin可以访问
                 */
                .and()
                .formLogin()  //配置Form表单登陆
                .loginPage("/page/auth/login")  //登陆页面地址（GET）
                .loginProcessingUrl("/api/auth/login") //form表单提交地址（POST）
                // .defaultSuccessUrl("/index", true) // 登录成功地址
                .successHandler(this::onAuthenticationSuccess)
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")    //退出登陆的请求地址
                .logoutSuccessUrl("/login")    //退出后重定向的地址
                .and()
                .csrf().disable() // 关闭csrf
                .rememberMe()   //开启记住我功能
                .rememberMeParameter("remember")  //登陆请求表单中需要携带的参数，如果携带，那么本次登陆会被记住
                .tokenValiditySeconds(60*60*24*7) // cookie 保存时间 以秒为单位, 7天
                // .tokenRepository(new InMemoryTokenRepositoryImpl()); //这里使用的是直接在内存中保存的TokenRepository实现 - 服务器重启cookie失效 - 但是这个不好最好用数据库存储
                .tokenRepository(repository);
        //TokenRepository有很多种实现，InMemoryTokenRepositoryImpl直接基于Map实现的，缺点就是占内存、服务器重启后记住我功能将失效
        //后面我们还会讲解如何使用数据库来持久化保存Token信息
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*// BCryptPasswordEncoder - 密码加密器 --- 直接认证登陆的方法
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  //这里使用SpringSecurity提供的BCryptPasswordEncoder
        auth
                .inMemoryAuthentication() //直接验证方式，之后会讲解使用数据库验证
                .passwordEncoder(encoder) //密码加密器 - Hash处理加密后解不了密 - 数据库存的是密文 - 比较密码是否一致需要重新加密看看加密后的密文是否相同
                .withUser("test")   //用户名
                .password(encoder.encode("123456"))   //这里需要填写加密后的密码
                .roles("user");   //用户的角色（之后讲解）*/

        auth
                .userDetailsService(service)   //使用自定义的Service实现类进行验证
                .passwordEncoder(new BCryptPasswordEncoder());   //依然使用BCryptPasswordEncoder

    }


    // index页面拿到User信息
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        AuthUser user = mapper.getPasswordByUsername(authentication.getName());
        session.setAttribute("user", user);
        if (user.getRole().equals("admin")){
            httpServletResponse.sendRedirect("/bookmanager/page/admin/index");
        } else {
            httpServletResponse.sendRedirect("/bookmanager/page/user/index");
        }
    }
}
