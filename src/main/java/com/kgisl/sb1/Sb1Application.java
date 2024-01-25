package com.kgisl.sb1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
@Configuration
@OpenAPIDefinition
@SpringBootApplication
public class Sb1Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Sb1Application.class, args);
	}


    // @Autowired
    // private LoggingInterceptor loggingInterceptor;
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
       
    //     registry.addInterceptor(loggingInterceptor);
	// 	System.out.println("AppConfig");
    // }
}
