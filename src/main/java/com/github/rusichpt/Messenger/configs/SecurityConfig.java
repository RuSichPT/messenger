package com.github.rusichpt.Messenger.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
//                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).permitAll()
                        .anyRequest().permitAll()
                )
                .headers(headers -> headers
                        .frameOptions(Customizer.withDefaults())
                        .disable()) // чтобы работала h2-console
                .formLogin(Customizer.withDefaults()
                )
                .logout(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))// чтобы работала h2-console
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/api/**")));

        return http.build();
    }

}
