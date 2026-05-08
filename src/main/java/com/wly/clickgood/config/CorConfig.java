package com.wly.clickgood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置。
 *
 * <p>当前端和后端端口或域名不一样时，浏览器会触发跨域限制，这里统一放开接口访问。</p>
 */
@Configuration
public class CorConfig implements WebMvcConfigurer {

    /**
     * 配置哪些接口、请求方法和请求头允许跨域访问。
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
