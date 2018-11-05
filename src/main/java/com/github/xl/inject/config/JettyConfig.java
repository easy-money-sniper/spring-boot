package com.github.xl.inject.config;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/01 14:33
 */
@Configuration
public class JettyConfig implements WebServerFactoryCustomizer<JettyServletWebServerFactory> {
    @Override
    public void customize(JettyServletWebServerFactory factory) {

    }
}
