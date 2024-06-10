package com.npt.anis.ANIS.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    //     CORS 설정 MVC
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://localhost:3000", "https://192.168.0.3:3000", "https://kingfish-sound-goshawk.ngrok-free.app:80")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .exposedHeaders("access"); // 'access' 헤더를 클라이언트에서 접근 가능하도록 설정
    }
}
