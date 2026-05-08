package com.wly.clickgood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类。
 *
 * <p>@SpringBootApplication 会让 Spring Boot 从当前包开始扫描 Controller、配置类、异常处理器等组件。</p>
 */
@SpringBootApplication
public class ClickGoodApplication {

    /**
     * 程序入口，启动内置 Web 服务。
     */
    public static void main(String[] args) {
        SpringApplication.run(ClickGoodApplication.class, args);
    }

}
