package com.npt.anis.ANIS.jwt.config;

import com.npt.anis.ANIS.jwt.filter.CustomLogoutFilter;
import com.npt.anis.ANIS.jwt.filter.JwtFilter;
import com.npt.anis.ANIS.jwt.filter.LoginFilter;
import com.npt.anis.ANIS.jwt.util.CookieUtil;
import com.npt.anis.ANIS.jwt.util.JwtUtil;
import com.npt.anis.ANIS.jwt.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    // AuthenticationManager가 인자로 받을 AuthenticationConfiguration을 주입받음
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final CookieUtil cookieUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // session 방식은 세션이 고정되기 때문에 방어를 해야하지만 jwt는 세션이 없기 때문에 disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        // formLogin 방식을 사용하지 않기 때문에 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        // httpBasic 방식을 사용하지 않기 때문에 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .headers((headers) -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/login", "/join", "/h2-console/**", "favicon.ico").permitAll()
                        .requestMatchers(PathRequest.toH2Console() + "/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/api/token/reissue", "/api/test").permitAll()
                        .anyRequest().authenticated()
                );

        // 로그인 전 토큰을 검증할 필터 추가
        http
                .addFilterAt(new JwtFilter(jwtUtil), LoginFilter.class);
        // JWT 필터를 위한 커스텀 필터 추가
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository, cookieUtil), UsernamePasswordAuthenticationFilter.class);

        // 커스텀 로그아웃 필터 추가
        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        // 세션을 사용하지 않기 때문에 STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // CORS 설정 시큐리티
        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration corsConfiguration = new CorsConfiguration();
                                corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                                corsConfiguration.setAllowCredentials(true);
                                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                                corsConfiguration.setMaxAge(3600L);

                                corsConfiguration.setExposedHeaders(Collections.singletonList("access")); // access 헤더를 노출

                                return corsConfiguration;
                            }
                        }));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
