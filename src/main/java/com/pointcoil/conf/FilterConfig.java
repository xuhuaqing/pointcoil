package com.pointcoil.conf;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:fusheng
 * @date:2019-04-14
 * @ver:1.0
 **/

@SpringBootConfiguration
public class FilterConfig implements WebMvcConfigurer {
    @Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedHeaders("*")
				.allowedMethods("*")
				.allowedOrigins("*")
				.allowCredentials(true);
	}


}
