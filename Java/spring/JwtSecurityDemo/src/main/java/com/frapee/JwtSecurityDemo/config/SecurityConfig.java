package com.frapee.JwtSecurityDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import com.frapee.JwtSecurityDemo.jwt.JwtAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());

        return authProvider;
    }

    @Bean
    JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }    

	@Bean
	UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withDefaultPasswordEncoder().username("bla").password("password").build());
		manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER") .build());
        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("password").roles("ADMIN").build());
		manager.createUser(User.withDefaultPasswordEncoder().username("author").password("password").authorities( "AUTHOR").build());
		return manager;
	}

	@Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
			.csrf((csrf) -> csrf
					.csrfTokenRepository(csrfTokenRepository())
					.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))		
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/signin")
                .permitAll()
                .requestMatchers("/v3/**","/swagger-ui/**")
                .permitAll()
            	.requestMatchers("/api/**")
				.hasRole("USER")
				.requestMatchers("/admin/**")
				.hasRole("ADMIN")
				.requestMatchers("/author/**")
				.hasAnyAuthority("AUTHOR")
                .requestMatchers("/public/**")
				.permitAll()
			)
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .build();            
    }

}
