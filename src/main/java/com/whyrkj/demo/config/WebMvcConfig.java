package com.whyrkj.demo.config;

import com.whyrkj.demo.config.interceptor.ParamInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

/**
 * @author zhangsanfeng
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);



    /**
     * 拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns用于添加拦截规则,excludePathPatterns用于排除拦截
        registry.addInterceptor(new ParamInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/om/admin/login")
                .excludePathPatterns("/upload")
                .excludePathPatterns("/download");
    }


    /**
     * 静态资源不拦截
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("/uploadFile/");
        registry.addResourceHandler("/attendance/image/**").addResourceLocations("/attendance/image/");
    }

    /**
     * 处理分页条件参数
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ServletWebArgumentResolverAdapter(new QueryParamsArgumentResolver()));
    }
}
