package com.igorivkin.creditconveyor.agreementgeneratorservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AgreementMvcConfig implements WebMvcConfigurer {

    /**
     * Перенаправляет вызовы на URL /agreements/** на статическую папку с файлами.
     * Это требуется для того, чтобы сервис мог открыть доступ к сгенерированным договорам.
     *
     * @param registry реестр путей для обработки ресурсов
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/agreements/**")
                .addResourceLocations("file:agreements/");
    }
}
