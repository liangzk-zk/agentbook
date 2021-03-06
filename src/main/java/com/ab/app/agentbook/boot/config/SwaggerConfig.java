/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ab.app.agentbook.boot.config;

import org.springframework.context.annotation.Bean;

import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger文档生成配置
 */
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .tags(
                new Tag("USER", "用户管理"),
                new Tag("BANKCARD", "银行卡信息管理"),
                new Tag("COMPANY","公司管理"),
                new Tag("PAYMENTVOUCHER","支付凭证"),
                new Tag("COIN","数字货币"),
                new Tag("LEVEL","等级管理"),
                new Tag("RECHARGE","用户额度")
//                new Tag("用户任务列表", ""), new Tag("任务处理", ""), new Tag("附加任务处理", ""), 
//                new Tag("流程实例", ""), new Tag("流程定义", ""), 
//                new Tag("流程管理", ""), new Tag("流程委托", ""), new Tag("流程跟踪", "")
                )
            .apiInfo(this.apiInfo()).select()
            .apis(Predicates.and(Predicates.or(RequestHandlerSelectors.basePackage("com.ab.app.agentbook.rest.mvc"),
                    RequestHandlerSelectors.basePackage("com.ab.app.agentbook.mobile.rest.mvc")),
                RequestHandlerSelectors.withClassAnnotation(Api.class)))
            
            // .paths(Predicates.or(PathSelectors.ant("/client/*"),
            // PathSelectors.ant("/clients/**")))
            .build();
    }

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("开放API接口文档").description("平台的接口文档").version("1.0.0")
            // .termsOfServiceUrl("www.baidu.com")
            // .license("LICENSE")
            // .licenseUrl("www.baidu.com")
            // .contact(new Contact("java程序员", "", ""))
            .build();
    }
}
