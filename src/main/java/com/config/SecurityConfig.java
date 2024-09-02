package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.JwtAuthenticationEntryPoint;
import com.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	 @Autowired
	    private JwtAuthenticationEntryPoint authenticationPoint;
	   
	 @Autowired
	    private JwtAuthenticationFilter filter;
	 
	 public static final String[] PUBLIC_URLS= {
				"/auth/**",
				"/v3/api-docs",
				"/v2/api-docs",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/webjars/**"
		};
	 
	 
	
	@Bean
	SecurityFilterChain getSecurity(HttpSecurity http) throws Exception {
		
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(authorize->authorize.
				requestMatchers(PUBLIC_URLS).permitAll().
				 requestMatchers("/admin/**").hasRole("ADMIN").
				 requestMatchers(HttpMethod.GET).permitAll().//in this generate documetation
				anyRequest()
				 .authenticated()).exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationPoint))
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//jwt work stateless machanism
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManger(AuthenticationConfiguration c) throws Exception {
		return c.getAuthenticationManager();
	}

}
