package com.classicmodel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/").setViewName("home");

		registry.addViewController("/employees").setViewName("employees");
		registry.addViewController("/offices").setViewName("offices");
		registry.addViewController("/customers").setViewName("customers");
		registry.addViewController("/orders").setViewName("orders");
		registry.addViewController("/productlines").setViewName("productlines");
		registry.addViewController("/analytics").setViewName("analytics");

		registry.addViewController("/employees/{id}").setViewName("employee-detail");
		registry.addViewController("/offices/{id}").setViewName("office-detail");
		registry.addViewController("/customers/{id}").setViewName("customer-detail");
		registry.addViewController("/orders/{id}").setViewName("order-detail");
		registry.addViewController("/productlines/{id}").setViewName("products-by-line");
		registry.addViewController("/products/{id}").setViewName("product-detail");
	}
}