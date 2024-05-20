package com.example.initializer;

import com.example.config.MvcConfiguration;
import com.example.config.RootConfiguration;
import com.example.config.SecurityConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    // 应用程序的根配置和安全配置
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfiguration.class, SecurityConfiguration.class};
    }

    // 定义了Spring MVC的配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfiguration.class};
    }

    // 指示Servlet匹配的地址为"/"，这意味着所有的请求都会被Spring的DispatcherServlet处理
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; // Servlet匹配的地址"/"
    }
}