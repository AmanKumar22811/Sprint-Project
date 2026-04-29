package com.classicmodel.config;

import com.classicmodel.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer, WebMvcConfigurer {

    @Autowired
    private Validator validator;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(
            Office.class,
            Employee.class,
            Customer.class,
            Order.class,
            ProductLine.class,
            Product.class
        );
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Home
        registry.addViewController("/").setViewName("home");

        // Master table pages (Page 2)
        registry.addViewController("/employees").setViewName("employees");
        registry.addViewController("/offices").setViewName("offices");
        registry.addViewController("/customers").setViewName("customers");
        registry.addViewController("/orders").setViewName("orders");
        registry.addViewController("/productlines").setViewName("productlines");
        registry.addViewController("/analytics").setViewName("analytics");

        // Detail pages (Page 3) — path variable wildcard matches single segment
        registry.addViewController("/employees/{id}").setViewName("employee-detail");
        registry.addViewController("/offices/{id}").setViewName("office-detail");
        registry.addViewController("/customers/{id}").setViewName("customer-detail");
        registry.addViewController("/orders/{id}").setViewName("order-detail");
        registry.addViewController("/productlines/{id}").setViewName("products-by-line");
        registry.addViewController("/products/{id}").setViewName("product-detail");
    }
}
