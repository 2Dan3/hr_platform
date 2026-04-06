package com.hr.management.candidates.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("*");
            }
        };
    }

    @Bean(name= {"messageSource"})
    public ResourceBundleMessageSource messageSource() {

        ResourceBundleMessageSource source = new ResourceBundleMessageSource();

        source.setBasenames("messages/messages");

        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding("UTF-8");

        source.setDefaultLocale(Locale.ENGLISH);
        return source;
    }

    @Bean
    public LocaleResolver localeResolver() {

        // keep language info in http cookies
        // else it invalidates with the whole http session on logout
        CookieLocaleResolver resolver = new CookieLocaleResolver();

        resolver.setDefaultLocale(Locale.ENGLISH);

        resolver.setCookieName("locale");

        resolver.setCookieMaxAge(3600 * 24 * 1);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("locale");
        return interceptor;
    }

    // Registering an interceptor with Spring(Boot) by implementing WebMvcConfigurer interface
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
