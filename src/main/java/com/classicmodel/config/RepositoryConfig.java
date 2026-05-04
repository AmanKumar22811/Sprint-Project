package com.classicmodel.config;

import com.classicmodel.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Spring Data REST configuration.
 * - Exposes entity IDs in responses.
 * - Registers a servlet-level CorsFilter (highest precedence) so the Thymeleaf
 *   frontend on port 8090 can call the API on port 8080 without browser errors.
 * - Validates entities via JSR-380 before create/save.
 */
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

	@Autowired
	private Validator validator;

<<<<<<< Updated upstream
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(Office.class, Employee.class, Customer.class, Order.class, ProductLine.class,
				Product.class);
	}

	@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
		validatingListener.addValidator("beforeCreate", validator);
		validatingListener.addValidator("beforeSave", validator);
	}
}
=======
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(
            Office.class,
            Employee.class,
            Customer.class,
            Order.class,
            OrderDetail.class,
            Payment.class,
            ProductLine.class,
            Product.class
        );

        // Spring Data REST CORS — covers /api/** endpoints inside Spring MVC
        cors.addMapping("/api/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(false);
    }

    /**
     * Servlet-level CORS filter that runs BEFORE Spring Security / MVC.
     * This is the reliable fix for cross-origin fetch() calls from the Thymeleaf
     * frontend (http://localhost:8090) to this API (http://localhost:8080).
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> globalCorsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }
}
>>>>>>> Stashed changes
