package com.ab.app.agentbook;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ab.app.agentbook.boot.config.SwaggerConfig;
@EnableScheduling // 开启定时任务功能
@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class})
@Import(SwaggerConfig.class)
@ImportResource("classpath*:META-INF/spring/*.xml")
public class AgentBookApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(AgentBookApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AgentBookApplication.class);
    }
}
