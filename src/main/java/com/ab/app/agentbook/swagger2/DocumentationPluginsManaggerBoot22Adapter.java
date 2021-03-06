package com.ab.app.agentbook.swagger2;
///*
// * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
// * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// */
//package com.ab.app.agentbook.swagger2;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.plugin.core.PluginRegistry;
//import org.springframework.stereotype.Component;
//
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.DefaultsProviderPlugin;
//import springfox.documentation.spi.service.ResourceGroupingStrategy;
//import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
//import springfox.documentation.spring.web.SpringGroupingStrategy;
//import springfox.documentation.spring.web.plugins.DefaultConfiguration;
//import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
//
///**
// * 对springfox swagger 2.9.2与spring boot 2.2不兼容的临时修正
// * 
// * @see <a href=
// *      "https://github.com/springfox/springfox/issues/2932">https://github.com/springfox/springfox/issues/2932</a>
// * @version 1.0, 2019年12月6日
// */
//@Component
//@Primary
//public class DocumentationPluginsManaggerBoot22Adapter extends DocumentationPluginsManager {
//    @Autowired
//    @Qualifier("resourceGroupingStrategyRegistry")
//    private PluginRegistry<ResourceGroupingStrategy, DocumentationType> resourceGroupingStrategies;
//    @Autowired
//    @Qualifier("defaultsProviderPluginRegistry")
//    private PluginRegistry<DefaultsProviderPlugin, DocumentationType> defaultsProviders;
//
//    @Override
//    public ResourceGroupingStrategy resourceGroupingStrategy(DocumentationType documentationType) {
//        return resourceGroupingStrategies.getPluginOrDefaultFor(documentationType, new SpringGroupingStrategy());
//    }
//
//    @Override
//    public DocumentationContextBuilder createContextBuilder(DocumentationType documentationType,
//            DefaultConfiguration defaultConfiguration) {
//        return defaultsProviders.getPluginOrDefaultFor(documentationType, defaultConfiguration)
//                .create(documentationType)
//                .withResourceGroupingStrategy(resourceGroupingStrategy(documentationType));
//    }
//}