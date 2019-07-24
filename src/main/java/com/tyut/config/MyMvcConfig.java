package com.tyut.config;

import com.tyut.component.LoginHandlerInterceptor;
import com.tyut.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

//使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * WebMvcConfigurationAdapter 在spring boot 2.0被废弃了，在P34如果遇到使用
     * WebMvcConfigurationSupport 而静态文件不显示CSS样式的，这是因为替换之后
     * 之前的静态资源文件 会被拦截，导致无法可用。
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//静态文件
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//webjar文件
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        registry.addResourceHandler("/emp/**").addResourceLocations("classpath:/static/");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送/hell请求来到success
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");

    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }


    //    注册拦截器
    // 将组件注册在容器中
    @Bean
    public WebMvcConfigurer webMvcConfigurationSupport(){
        WebMvcConfigurer adapter=new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
                        excludePathPatterns("/index.html","/","/userlogin","/asserts/**");
            }
        };
        return adapter;
    }
}
