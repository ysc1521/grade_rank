package com.ysc.graderank.config;

import com.ysc.graderank.intercept.WebHandleIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private WebHandleIntercept webHandleIntercept;

    public static final String[] EXCLUDE_PATHS = {"/login", "/index", "/user/login", "/user/logout", "/static/**", "/test/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webHandleIntercept).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATHS);
    }

}
