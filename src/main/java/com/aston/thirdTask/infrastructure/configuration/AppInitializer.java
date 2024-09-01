package com.aston.thirdTask.infrastructure.configuration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Initializes the web application by configuring the Servlet context programmatically.
 */
public class AppInitializer implements WebApplicationInitializer {

    /**
     * Configures the Servlet context with the Spring application context and registers the DispatcherServlet.
     *
     * @param servletContext the Servlet context to configure.
     * @throws ServletException if an error occurs during Servlet context configuration.
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfiguration.class); // Регистрация класса конфигурации

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
