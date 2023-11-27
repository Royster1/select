package com.example.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 访问页面
// 默认的页面所有人都可以访问的页面
@Controller
@RequestMapping("/page/auth")
public class AuthPageController {
    /*
    @Resource
    UserMapper mapper;

    @Resource
    SimpleService simpleService; // 导入依赖

    @PreAuthorize("hasAnyRole('user', 'admin')") // 可以作用域方法, 如果不是admin这个方法就调用不了
    @RequestMapping("/index")
    public String index(HttpSession session, Model model){
        // 授权过滤
        List<String> list = new LinkedList<>();
        list.add("lb");
        list.add("lbwnb");
        list.add("lbg");
        simpleService.test(list);

        // SecurityContextHolder 方便拿到 SecurityContext 对象
        // 使用 SecurityContext 对象 context 来获取当前的认证信息!
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication1 = context.getAuthentication(); // 返回授权信息对象 authentication
        User user1 = (User) authentication1.getPrincipal(); // getPrincipal 获得user对象
        System.out.println(user1.getUsername());
        System.out.println(user1.getAuthorities());

        //
        AuthUser user = (AuthUser) session.getAttribute("user");
        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user = mapper.getPasswordByUsername(authentication.getName());
            session.setAttribute("user", user);
        }
        model.addAttribute("user", user);
        return "index";
    }*/

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }



    /*// 管理员能够登陆的页面
    @PreAuthorize("hasRole('admin')")   //判断是否为user角色，只有此角色才可以访问
    @RequestMapping("/admin")
    public String admin(){
        return "index";
    }
*/

    // 强制性登录 手动添加一个认证信息 手动登录
    /*
    @RequestMapping("/auth")
    @ResponseBody
    public String auth(){
        SecurityContext context = SecurityContextHolder.getContext();  //获取SecurityContext对象（当前会话肯定是没有登陆的）
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user"));  //手动创建一个UsernamePasswordAuthenticationToken对象，也就是用户的认证信息，角色需要添加ROLE_前缀，权限直接写
        context.setAuthentication(token);  //手动为SecurityContext设定认证信息
        return "Login success！";
    }
    */

}
