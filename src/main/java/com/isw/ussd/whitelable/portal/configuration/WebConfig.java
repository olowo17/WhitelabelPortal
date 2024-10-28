package com.isw.ussd.whitelable.portal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CorsFilter corsFilter() {



            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            final CorsConfiguration config = new CorsConfiguration();

            // Allow credentials (cookies, etc.) to be included in requests
            config.setAllowCredentials(false);

            // Allow specific origin (adjust this to your client-side origin)
           // config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            config.setAllowedOrigins(Collections.singletonList("*"));

            // Allowed headers
            config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "appversion", "countrycode", "deviceid", "languagecode", "sessionid"));

            // Allowed HTTP methods
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));

            // Register the configuration for all paths
            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
        }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(false);
//

//
//        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "appversion", "countrycode", "deviceid", "languagecode", "sessionid"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);

}
