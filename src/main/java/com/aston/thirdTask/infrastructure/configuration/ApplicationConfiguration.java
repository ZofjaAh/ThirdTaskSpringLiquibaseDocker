package com.aston.thirdTask.infrastructure.configuration;

import com.aston.thirdTask.ComponentScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Spring configuration class for setting up web MVC components.
 */
@Configuration
@ComponentScan(basePackageClasses = ComponentScanMarker.class)
@Import(PersistenceJPAConfiguration.class)
@EnableWebMvc

public class ApplicationConfiguration implements WebMvcConfigurer {
    /**
     * Configures the message converters for the application.
     *
     * @param converters the list of message converters to configure.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
