package com.surittec.spring.boot.crud.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
        corsConfiguration.setAllowCredentials(false);
        
        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                .authenticated().and().csrf().disable().cors().configurationSource(request -> corsConfiguration);

    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
